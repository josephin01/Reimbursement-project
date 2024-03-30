package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.FilterDto;
import com.reimbursement.project.dto.ReportDetailsDto;
import com.reimbursement.project.dto.ReportDto;
import com.reimbursement.project.entity.*;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.repository.service.*;
import com.reimbursement.project.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ExpensesRepoService expensesRepoService;

    private final ExpenseClaimsRepoService expenseClaimsRepoService;

    private final TravelFormRepoService travelFormRepoService;

    private final ProjectsRepoService projectsRepoService;

    private final ExpenseTypeRepoService expenseTypeRepoService;

    private int count = 1;
    private final List<ReportDto> report = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public void exportReport(HttpServletResponse response, FilterDto filterDto) throws IOException, JRException, ParseException {

        String startDate = filterDto.getStartDate();
        String endDate = filterDto.getEndDate();
        List<String> projectName = filterDto.getProjectName();
        String reportType = filterDto.getReportType();
        List<String> expenseType = filterDto.getExpenses();
        List<Long> findExpenseId = new ArrayList<>();
        List<Long> findProjectId = new ArrayList<>();

        if (projectName != null) {
            findProjectId = getProjectId(projectName);
        }

        Date fromDate = null;
        Date toDate = null;

        if (startDate != null) {
            fromDate = dateFormat.parse(startDate);
        }
        if (endDate != null) {
            toDate = dateFormat.parse(endDate);
        }
        report.clear();
        count = 1;
        if (expenseType != null) {
            findExpenseId = getExpenseTypeExpenseClaims(expenseType);
            getExpenseClaimsDetails(filterDto, fromDate, toDate, findProjectId, findExpenseId);
            findExpenseId.clear();

           findExpenseId=getExpenseTypeTravelForm(expenseType);
            getTravelFormExpenses(filterDto, fromDate, toDate, findExpenseId, findProjectId);
        }

        if (expenseType == null) {
            getExpenseClaimsDetails(filterDto, fromDate, toDate, findProjectId, findExpenseId);
            getTravelFormExpenses(filterDto, fromDate, toDate, findExpenseId, findProjectId);

        }

        InputStream inputStream = getClass().getResourceAsStream("/Report.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException(Constant.JRXML_FILE_NOT_FOUND);
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(report);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        if ("pdf".equals(reportType)) {

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=Report.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } else if ("excel".equals(reportType)) {

            JRXlsxExporter exporter = new JRXlsxExporter();
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setSheetNames(new String[]{"sheet1"});
            exporter.setConfiguration(reportConfigXLS);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setHeader("Content-Disposition", "attachment;filename=Report.xlsx");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            exporter.exportReport();
        }
    }

    private List<Long> getExpenseTypeTravelForm(List<String> expensetype) {
        List<Long> expenseId=new ArrayList<>();
        for (String expense : expensetype) {
            expense = StringUtils.capitalize(expense.toLowerCase());
            Optional<ExpenseType> travelExpenseType = expenseTypeRepoService.checkExpenseType(expense, FormType.TRAVEL_FORM);
            if (travelExpenseType.isPresent()) {
                ExpenseType expenseType1 = travelExpenseType.get();
                expenseId.add(expenseType1.getId());
            }
        }
        return expenseId;
    }

    private List<Long> getExpenseTypeExpenseClaims(List<String> expenses) {
        List<Long> expenseId = new ArrayList<>();
        for (String expense : expenses) {
            expense = StringUtils.capitalize(expense.toLowerCase());
            Optional<ExpenseType> expenseType = expenseTypeRepoService.checkExpenseType(expense, FormType.EXPENSE_CLAIMS);

            if (expenseType.isPresent()) {
                ExpenseType expenseType1 = expenseType.get();
                expenseId.add(expenseType1.getId());
            }

        }
        return expenseId;
    }

    private List<Long> getProjectId(List<String> projectName) {
        List<Long> proj=new ArrayList<>();
        for(String project:projectName) {
            project = StringUtils.capitalize(project.toLowerCase());
            Long projId = projectsRepoService.getProjectId(project);
            proj.add(projId);

        }
        return  proj;
    }

    public void getExpenseClaimsDetails(FilterDto filterDto, Date fromDate,Date toDate,List<Long> findProjectId,List<Long> findExpenseId){
        List<Long> empId = filterDto.getEmpId();
        Float minAmount = filterDto.getMinAmount();
        Float maxAmount = filterDto.getMaxAmount();
        ExpenseStatus expenseStatus = filterDto.getExpenseStatus();

        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.toFindAll();

        for (ExpenseClaims expenseClaim : expenseClaims) {

            ReportDto reportDto = new ReportDto();

            Long fetchEmp = expenseClaim.getEmployeeDetails().getEmpId();
            Long fetchProject = expenseClaim.getProjects().getId();
            ExpenseStatus fetchExpenseStatus = expenseClaim.getExpenseStatus();
            Long fetchExpenseType = expenseClaim.getExpenseType().getId();
            Date fetchDate = expenseClaim.getApplyDate();
            Float fetchAmount = expenseClaim.getExpenseAmount();

            boolean checkProject=(findProjectId.isEmpty() || findProjectId.contains(fetchProject));
            boolean isValidDateAndAmount = isFilterDateAndAmount(maxAmount, minAmount, fromDate, toDate, fetchAmount, fetchDate);
            boolean isValidExpense= isFilterExpense(empId,fetchEmp,expenseStatus,fetchExpenseStatus,findExpenseId,fetchExpenseType);


            if (isValidDateAndAmount && isValidExpense && checkProject ){
                    reportDto.setSNO(count);
                    reportDto.setAmount(fetchAmount);
                    reportDto.setProjectName(expenseClaim.getProjects().getProjectName());
                    reportDto.setEmployeeID(fetchEmp);
                    reportDto.setExpenseType(expenseClaim.getExpenseType().getExpenses());
                    reportDto.setManagerName(expenseClaim.getManagers().getManagerName());
                    reportDto.setStatus(expenseClaim.getExpenseStatus().name().replace("_", " "));
                    reportDto.setAppliedDate(fetchDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    report.add(reportDto);

                    count++;
                }
            }

    }

    public void getTravelFormExpenses(FilterDto filterDto, Date fromDate,Date toDate,List<Long> findExpenseId,List<Long> findProjectId) {
        List<Long> empId = filterDto.getEmpId();
        Float minAmount = filterDto.getMinAmount();
        Float maxAmount = filterDto.getMaxAmount();
        ExpenseStatus expenseStatus = filterDto.getExpenseStatus();
        List<Expenses> expenses = expensesRepoService.toFindAll();

        for (Expenses expense : expenses) {

            ReportDetailsDto reportDetailsDto = getTravelFormDetails(expense.getTravelForm().getId());
            ReportDto reportDto = new ReportDto();
            Long fetchEmp = reportDetailsDto.getEmployeeId();
            Projects project=reportDetailsDto.getProject();
            Long fetchProject =project.getId();
            ExpenseStatus fetchExpenseStatus = expense.getExpenseStatus();
            Long fetchExpenseType = expense.getExpenseType().getId();
            Date fetchDate = reportDetailsDto.getApplyDate();
            Float fetchAmount = expense.getExpenseAmount();

            boolean checkProject=(findProjectId.isEmpty()|| findProjectId.contains(fetchProject));
            boolean isValidDateAndAmount = isFilterDateAndAmount(maxAmount, minAmount, fromDate, toDate, fetchAmount, fetchDate);
            boolean isValidExpense= isFilterExpense(empId,fetchEmp,expenseStatus,fetchExpenseStatus,findExpenseId,fetchExpenseType);

            if (isValidDateAndAmount && isValidExpense && checkProject) {

                    reportDto.setSNO(count);
                    reportDto.setAmount(fetchAmount);
                    reportDto.setProjectName(project.getProjectName());
                    reportDto.setEmployeeID(fetchEmp);
                    reportDto.setExpenseType(expense.getExpenseType().getExpenses());
                    reportDto.setManagerName(reportDetailsDto.getManagerName());
                    reportDto.setStatus(expense.getExpenseStatus().name().replace("_", " "));
                    reportDto.setAppliedDate(fetchDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    report.add(reportDto);

                    count++;
                }
        }
    }

    private boolean isFilterDateAndAmount(Float maxAmount, Float minAmount, Date fromDate, Date toDate, Float fetchAmount, Date fetchDate) {
        boolean checkAmount = isValidAmount(maxAmount,minAmount,fetchAmount);
        boolean checkDate = isValidDate(fromDate,toDate,fetchDate);

        return checkAmount && checkDate;

    }

    private boolean isValidDate(Date fromDate, Date toDate, Date fetchDate) {
        if (fromDate == null &&toDate == null) {
            return true;
        } else if (fromDate != null && toDate != null) {
            return (fetchDate.compareTo(toDate) <= 0 && fetchDate.compareTo(fromDate) >= 0);

        } else if (fromDate != null) {
            return (fetchDate.compareTo(fromDate) >= 0);

        } else {
            return (fetchDate.compareTo(toDate) <= 0);

        }
    }

    private boolean isValidAmount(Float maxAmount, Float minAmount, Float fetchAmount) {
        if (maxAmount == null && minAmount == null) {
           return true;
        } else if (maxAmount != null && minAmount != null) {
            return (fetchAmount <= maxAmount && fetchAmount >= minAmount);

        } else if (maxAmount != null) {
            return  (fetchAmount <= maxAmount);

        } else {
            return  (fetchAmount >= minAmount);
        }
    }

    private boolean isFilterExpense(List<Long> empId, Long fetchEmp, ExpenseStatus expenseStatus, ExpenseStatus fetchExpenseStatus, List<Long> findExpenseId, Long fetchExpenseType) {
        return ((empId.isEmpty() || empId.contains(fetchEmp)) && (expenseStatus == null || Objects.equals(fetchExpenseStatus, expenseStatus)) && (findExpenseId.isEmpty()|| findExpenseId.contains(fetchExpenseType)));
    }

    public ReportDetailsDto getTravelFormDetails(Long id) {
        TravelForm travelForm = travelFormRepoService.toFindById(id).orElseThrow(null);

        ReportDetailsDto reportDetailsDto = new ReportDetailsDto();

        EmployeeDetails employeeDetails = travelForm.getEmployeeDetails();
        reportDetailsDto.setEmployeeId(employeeDetails.getEmpId());

        Projects projects = travelForm.getProject();
        reportDetailsDto.setProject(projects);

        Managers managers = travelForm.getManagers();
        reportDetailsDto.setManagerName(managers.getManagerName());

        reportDetailsDto.setApplyDate(travelForm.getApplyDate());

        return reportDetailsDto;


    }
}

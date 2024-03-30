package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.*;
import com.reimbursement.project.entity.Enum.*;
import com.reimbursement.project.exception.AlreadyExistException;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.*;
import com.reimbursement.project.service.ExpensesService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpensesRepoService expensesRepoService;
    private final TravelFormRepoService travelFormRepoService;
    private final NotificationRepoService notificationRepoService;
    private final ExpenseTypeRepoService expenseTypeRepoService;
    private final BillsRepoService billsRepoService;
    private final EmployeeDetailsRepoService employeeDetailsRepoService;
    private final ModelMapper modelMapper;
    private  final RolesRepoService rolesRepoService;


    String employee="EMPLOYEE";
    String manager="MANAGER";
    String admin= "ADMIN";
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> storeExpenses(ExpensesDto expensesDto) {

        Optional<TravelForm> travelForm = travelFormRepoService.toFindById(expensesDto.getId());
        if (travelForm.isEmpty()) {
            throw new ResourceNotFoundException(Constant.TRAVEL_FORM_NOT_EXISTS);
        }

        TravelForm travelForm1 = travelForm.get();
        if (!travelForm1.getTravelFormStatus().equals(TravelFormStatus.FORM_APPROVED)) {
            throw new InvalidException(Constant.TRAVEL_FORM_NOT_APPROVED);
        }

        if (!travelForm1.getBillStatus().equals(BillStatus.NO_BILL)) {
            throw new AlreadyExistException(Constant.ALREADY_BILL_ADDED_TRAVEL_FORM);
        }

        Notification notification = new Notification();
        notification.setAdminNotificationStatus(NotifyStatus.SENT);
        notification.setManagerNotificationStatus(NotifyStatus.SENT);
        notification.setNotificationFormType(NotificationFormType.EXPENSE);
        notification.setEmpId(travelForm1.getEmployeeDetails().getId());
        notification.setManagerId(travelForm1.getManagers().getId());
        Date date = new Date();
        notification.setDate(date);
        notificationRepoService.toSave(notification);

        List<ExpensesListDto> expensesListDtos = expensesDto.getExpensesList();
        if (expensesListDtos == null) {
            throw new ResourceNotFoundException(Constant.EXPENSES_NOT_FOUND);
        }

        List<Expenses> expensesList = expensesListDtos
                .stream()
                .map(expensesListDto -> {
                    Expenses expenses1 = new Expenses();
                    expenses1.setTravelForm(travelForm1);

                    ExpenseType expenseType1 = expenseTypeRepoService.toFindById(expensesListDto.getExpenseType().getId()).get();
                    expenses1.setExpenseType(expenseType1);

                    expenses1.setExpenseDescription(expensesListDto.getExpenseDescription());
                    expenses1.setExpenseDate(expensesListDto.getExpenseDate());
                    expenses1.setExpenseAmount(expensesListDto.getExpenseAmount());
                    expenses1.setExpenseStatus(ExpenseStatus.PENDING);
                    expenses1.setApplyDate(expensesListDto.getApplyDate());

                    Notification notification1 = notificationRepoService.toFindById(notification.getId()).get();
                    expenses1.setNotification(notification1);
                    return expenses1;
                }).toList();
        List<Expenses> savedExpenses = expensesRepoService.toSaveAll(expensesList);


        for (int i = 0; i < savedExpenses.size(); i++) {
            Expenses savedExpense = savedExpenses.get(i);
            ExpensesListDto expensesListDto = expensesListDtos.get(i);

            List<BillsDto> billsDto = expensesListDto.getBills();
            if (billsDto != null) {
                List<Bills> bills = billsDto.stream()
                        .map(billsDto1 -> {
                            Bills bills1 = new Bills();
                            bills1.setBillsUrl(billsDto1.getBillsUrl());
                            bills1.setBillType(billsDto1.getBillType());
                            bills1.setBillName(billsDto1.getBillName());
                            bills1.setExpenses(savedExpense);
                            return bills1;
                        }).toList();
                billsRepoService.toSaveAll(bills);

            }
        }

        travelForm1.setBillStatus(BillStatus.BILL_ADDED);
        travelFormRepoService.toSave(travelForm1);

        List<ExpensesListDto> expensesListDto = new ArrayList<>();
        for (Expenses expensesListDto1 : savedExpenses) {
            expensesListDto.add(convertToExpenseDto(expensesListDto1));
        }

        ExpensesDto expensesDto1 = new ExpensesDto();
        expensesDto1.setId(expensesDto.getId());
        expensesDto1.setExpensesList(expensesListDto);

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSES_SAVED, expensesDto1));

    }

    private ExpensesListDto convertToExpenseDto(Expenses expensesList) {
        ExpenseType expenseType = expensesList.getExpenseType();
        ExpenseTypeDto expenseTypeDto = modelMapper.map(expenseType, ExpenseTypeDto.class);
        ExpensesListDto expensesListDto = modelMapper.map(expensesList, ExpensesListDto.class);
        List<BillsDto> billsDtoList = new ArrayList<>();
        List<Bills> bills = billsRepoService.toFindAllByExpensesId(expensesList.getId());

        for (Bills bills1 : bills) {
            BillsDto billsDto1 = modelMapper.map(bills1, BillsDto.class);
            billsDtoList.add(billsDto1);
        }

        expensesListDto.setBills(billsDtoList);
        expensesListDto.setExpenseType(expenseTypeDto);
        expensesListDto.setApplyDate(expensesList.getApplyDate());
        return expensesListDto;
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenses(Long id) {
        Optional<Expenses> expenses = expensesRepoService.toFindById(id);
        if (expenses.isPresent()) {
            ExpenseResponseDto expenseResponseDto = modelMapper.map(expenses, ExpenseResponseDto.class);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSES_FETCHED, expenseResponseDto));
        }
        throw new ResourceNotFoundException(Constant.EXPENSE_ID_NOT_FOUND);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateExpenses(Long id, ExpenseIdDto expenseIdDto) {
        Optional<TravelForm> travelForm = travelFormRepoService.toFindById(id);
             if (travelForm.isEmpty()) {
                throw new ResourceNotFoundException(Constant.TRAVEL_FORM_ID_NOT_FOUND);
            }

            TravelForm travelForm1 = travelForm.get();
             Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findById(travelForm1.getEmployeeDetails().getId());
             if(employeeDetails.isEmpty()){
                 throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
             }

            List<Expenses> expenses = expensesRepoService.toFindAllByIdOrderByCreatedAtDesc(id);

            for (Expenses expenses1 : expenses) {
                updateExpenseStatus(expenses1, expenseIdDto, travelForm1,employeeDetails.get().getIsManager());
            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.STATUS_UPDATED, null));
        }

        private void updateExpenseStatus (Expenses expenses, ExpenseIdDto expenseIdDto, TravelForm travelForm,Boolean isManager){
            ExpenseStatus expenseStatus = ExpenseStatus.valueOf(expenseIdDto.getExpenseStatus().toUpperCase());

            if ((expenseIdDto.getExpenseStatus().equals("MANAGER_APPROVED") || expenseIdDto.getExpenseStatus().equals("MANAGER_REJECTED"))
                    && !expenses.getExpenseStatus().toString().equals("PENDING")) {
                throw new InvalidException(Constant.EXPENSE_FORM_STATUS_UPDATED);
            }

            if ((expenseIdDto.getExpenseStatus().equals("ADMIN_APPROVED") || expenseIdDto.getExpenseStatus().equals("ADMIN_REJECTED"))
                    && (!expenses.getExpenseStatus().toString().equals("MANAGER_APPROVED"))) {
                throw new InvalidException(Constant.MANAGER_NOT_APPROVED);
            }

            expenses.setExpenseStatus(expenseStatus);
            expensesRepoService.toSave(expenses);

            if (expenseIdDto.getExpenseStatus().equals("ADMIN_REJECTED") || expenseIdDto.getExpenseStatus().equals("MANAGER_REJECTED")) {
                travelForm.setRemarks(expenseIdDto.getRemarks());
                travelFormRepoService.toSave(travelForm);
            }

            ExpenseIdDto updatedExpenseIdDto = modelMapper.map(expenses, ExpenseIdDto.class);
            updatedExpenseIdDto.setRemarks(expenseIdDto.getRemarks());
        }


        @Override
        public ResponseEntity<ApiResponseDto> getAllExpenses (String expenseStatus, RolesUpdateDto rolesDto){

            if ((expenseStatus.equals("ALL")) && rolesDto.getRoleName().equals(manager) && rolesDto.getIsTeam().equals(true)) {
                Optional<EmployeeDetails> employeeDetails1 = employeeDetailsRepoService.toFindByEmpId(rolesDto.getEmpId());
                if (employeeDetails1.isEmpty()) {
                    throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
                }
                EmployeeDetails employeeDetails = employeeDetails1.get();
                Long managerId = employeeDetails.getManagers().getId();
                List<TravelForm> travelForms = travelFormRepoService.getAllTravelFormsForManager(managerId);

                Map<TravelForm, Float> expenseTableResponseDto = getTravelFormExpenses(travelForms);

                List<ExpenseTableAdminResponseDto> expenseTableResponseList = getAdminResponseByTravelFormAmount(expenseTableResponseDto);
                expenseTableResponseList.sort(Comparator.comparing(ExpenseTableAdminResponseDto::getId));
                Collections.reverse(expenseTableResponseList);

                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.ALL_EXPENSES_FETCHED, expenseTableResponseList));
            }

            Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.toFindByEmpId(rolesDto.getEmpId());
            if (employeeDetails.isEmpty()) {
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
            }

            EmployeeDetails employeeDetails1 = employeeDetails.get();
            ExpenseStatus expenseStatus1 = ExpenseStatus.valueOf(expenseStatus.toUpperCase());
            if ((rolesDto.getRoleName().equals(employee)) || (rolesDto.getRoleName().equals(manager) && rolesDto.getIsTeam().equals(false))) {
                List<TravelForm> travelForms = travelFormRepoService.getAllTravelForms(rolesDto.getEmpId());
                Map<TravelForm, Float> result = filteredTravelForms(travelForms, expenseStatus1);

                List<ExpenseTableResponseDto> expenseTableResponseList = getExpenseTableByTravelFormAmount(result);
                expenseTableResponseList.sort(Comparator.comparing(ExpenseTableResponseDto::getId));
                Collections.reverse(expenseTableResponseList);

                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EMPLOYEE_EXPENSES_FETCHED, expenseTableResponseList));
            }
            else if ((rolesDto.getRoleName().equals(manager)) && rolesDto.getIsTeam().equals(true)) {
                Long managerId = employeeDetails1.getManagers().getId();
                List<TravelForm> travelForms = travelFormRepoService.getAllTravelFormsForManager(managerId);
                Map<TravelForm, Float> result = filteredTravelForms(travelForms, expenseStatus1);

                List<ExpenseTableAdminResponseDto> expenseTableResponseList = getAdminResponseByTravelFormAmount(result);
                expenseTableResponseList.sort(Comparator.comparing(ExpenseTableAdminResponseDto::getId));
                Collections.reverse(expenseTableResponseList);

                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.MANAGER_EXPENSES_FETCHED, expenseTableResponseList));
            }
            else if (rolesDto.getRoleName().equals(admin)) {
                List<TravelForm> travelForms = travelFormRepoService.findAllTravelForm();
                Map<TravelForm, Float> result = filteredTravelForms(travelForms, expenseStatus1);

                List<ExpenseTableAdminResponseDto> expenseTableResponseList = getAdminResponseByTravelFormAmount(result);
                expenseTableResponseList.sort(Comparator.comparing(ExpenseTableAdminResponseDto::getId));
                Collections.reverse(expenseTableResponseList);

                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.ADMIN_EXPENSES_FETCHED, expenseTableResponseList));

            }
            return null;
        }

    private List<ExpenseTableAdminResponseDto> getAdminResponseByTravelFormAmount(Map<TravelForm, Float> result) {
        List<ExpenseTableAdminResponseDto> expenseTableResponseList = new ArrayList<>();
        for (Map.Entry<TravelForm, Float> entry : result.entrySet()) {
            TravelForm travelForm = entry.getKey();
            Float expenseAmount = entry.getValue();

            expenseTableResponseList.add(convertToAdminResponse(travelForm, expenseAmount));
        }
        return expenseTableResponseList;
    }

    private List<ExpenseTableResponseDto> getExpenseTableByTravelFormAmount(Map<TravelForm, Float> result) {
        List<ExpenseTableResponseDto> expenseTableResponseList=new ArrayList<>();
        for (Map.Entry<TravelForm, Float> entry : result.entrySet()) {
            TravelForm travelForm = entry.getKey();
            Float expenseAmount = entry.getValue();
            expenseTableResponseList.add(convertToExpenseTable(travelForm, expenseAmount));
        }
        return expenseTableResponseList;
    }

    private Map<TravelForm, Float> getTravelFormExpenses(List<TravelForm> travelForms) {
        Map<TravelForm, Float> expenseTableResponseDto = new HashMap<>();
        for (TravelForm travelForm : travelForms) {
            List<Expenses> expenses = travelForm.getExpenses();
            Float expenseAmount = 0F;
            for (Expenses expenses1 : expenses) {
                expenseAmount += expenses1.getExpenseAmount();
            }
            expenseTableResponseDto.put(travelForm, expenseAmount);
        }
        return expenseTableResponseDto;
    }

    private ExpenseTableResponseDto convertToExpenseTable (TravelForm travelForm, Float expenseAmount){

        ExpenseTableResponseDto expenseTableResponseDto = new ExpenseTableResponseDto();
            expenseTableResponseDto.setId(travelForm.getId());
            expenseTableResponseDto.setExpenseAmount(expenseAmount);
            expenseTableResponseDto.setExpenseDate(travelForm.getApplyDate());
            expenseTableResponseDto.setProjectName(travelForm.getProject().getProjectName());
            if (!travelForm.getExpenses().isEmpty()) {
                expenseTableResponseDto.setExpenseStatus(travelForm.getExpenses().get(0).getExpenseStatus().toString());
            }
            return expenseTableResponseDto;

        }


        private ExpenseTableAdminResponseDto convertToAdminResponse (TravelForm travelForm, Float expenseAmount){
            ExpenseTableAdminResponseDto expenseTableResponseDto1 = new ExpenseTableAdminResponseDto();
            expenseTableResponseDto1.setId(travelForm.getId());
            expenseTableResponseDto1.setExpenseAmount(expenseAmount);
            expenseTableResponseDto1.setExpenseDate(travelForm.getApplyDate());
            expenseTableResponseDto1.setProjectName(travelForm.getProject().getProjectName());
            expenseTableResponseDto1.setFirstName(travelForm.getEmployeeDetails().getFirstName());
            expenseTableResponseDto1.setProfile(travelForm.getEmployeeDetails().getProfile());
            if (!travelForm.getExpenses().isEmpty()) {
                expenseTableResponseDto1.setExpenseStatus(travelForm.getExpenses().get(0).getExpenseStatus().toString());
            }
            return expenseTableResponseDto1;
        }

        private Map<TravelForm, Float> filteredTravelForms (List < TravelForm > travelForms, ExpenseStatus
        expenseStatus1){
            Map<TravelForm, Float> filteredTravelFormsMap = new HashMap<>();

            for (TravelForm travelForm : travelForms) {
                boolean flag = false;
                Float expenseAmount = 0F;

                for (Expenses expense : travelForm.getExpenses()) {
                    expenseAmount += expense.getExpenseAmount();
                    if (expenseStatus1.equals(expense.getExpenseStatus())) {
                        flag = true;
                    }
                }

                if (flag) {
                    filteredTravelFormsMap.put(travelForm, expenseAmount);
                }
            }

            return filteredTravelFormsMap;
        }


        public ResponseEntity<ApiResponseDto> getExpensesCount (Long empId, DashboardDto dashboardDto){
            Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.toFindByEmpId(empId);
            if (employeeDetails.isEmpty()) {
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
            }
            EmployeeDetails employeeDetails1 = employeeDetails.get();

            List<TravelForm> travelForms = travelFormRepoService.findAllByEmployeeDetails(employeeDetails1);

            List<Long> travelFormIds = travelForms.stream().map(TravelForm::getId).toList();
            List<Map<String, Object>> expensesStatusCount = new ArrayList<>();

            if (employeeDetails1.getRole().getRoleName().equals(employee)) {
                expensesStatusCount = expensesRepoService.expensesCountByTravelFormId(travelFormIds);
            } else if (employeeDetails1.getRole().getRoleName().equals(admin)) {
                expensesStatusCount = expensesRepoService.expensesStatusCount(empId);
            } else if (employeeDetails1.getRole().getRoleName().equals(manager)) {
                if (dashboardDto.getIsTeam().equals(false)) {
                    expensesStatusCount = expensesRepoService.expensesCountByTravelFormId(travelFormIds);
                } else {
                    Long managerId = employeeDetails1.getManagers().getId();
                    List<TravelForm> travelForms1 = travelFormRepoService.getAllTravelFormsForManager(managerId);
                    List<Long> travelFormId = travelForms1.stream().map(TravelForm::getId).toList();
                    expensesStatusCount = expensesRepoService.expensesCountByTravelFormId(travelFormId);
                }
            }

            Set<ExpenseStatus> expenseStatuses = new HashSet<>();
            List<Map<String, Object>> countOfExpenses = new ArrayList<>();
            String status1 = "expense_status";

            for (Map<String, Object> expenses : expensesStatusCount) {
                Map<String, Object> newExpense = new HashMap<>(Map.copyOf(expenses));
                short status = (short) expenses.get(status1);
                switch (status) {
                    case 0:
                        newExpense.put(status1, ExpenseStatus.PENDING);
                        expenseStatuses.add(ExpenseStatus.PENDING);
                        break;
                    case 1:
                        newExpense.put(status1, ExpenseStatus.MANAGER_APPROVED);
                        expenseStatuses.add(ExpenseStatus.MANAGER_APPROVED);
                        break;
                    case 2:
                        newExpense.put(status1, ExpenseStatus.MANAGER_REJECTED);
                        expenseStatuses.add(ExpenseStatus.MANAGER_REJECTED);
                        break;
                    case 3:
                        newExpense.put(status1, ExpenseStatus.ADMIN_APPROVED);
                        expenseStatuses.add(ExpenseStatus.ADMIN_APPROVED);
                        break;
                    default:
                        newExpense.put(status1, ExpenseStatus.ADMIN_REJECTED);
                        expenseStatuses.add(ExpenseStatus.ADMIN_REJECTED);
                        break;
                }
                countOfExpenses.add(newExpense);
            }
            for (ExpenseStatus expenseStatus : ExpenseStatus.values()) {
                if (!expenseStatuses.contains(expenseStatus)) {
                    Map<String, Object> addNew = new HashMap<>();
                    addNew.put("expense_status", expenseStatus);
                    addNew.put("expense_status_count", 0);
                    countOfExpenses.add(addNew);
                }
            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSES_COUNT_FETCHED, countOfExpenses));

        }


    @Override
    public ResponseEntity<ApiResponseDto> getExpenseStatus() {
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_STATUS_FETCHED,ExpenseStatus.values()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpensesById(Long id) {
        List<Expenses> expensesList = expensesRepoService.toFindAllByIdOrderByCreatedAtDesc(id);

        if (expensesList.isEmpty()) {
            throw new ResourceNotFoundException(Constant.NO_EXPENSES_TRAVEL_FORM);
        }
        Optional<TravelForm> travelForm = travelFormRepoService.toFindById(id);
        ExpensesDto expensesDto = new ExpensesDto();
        if (travelForm.isPresent()) {
            TravelForm travelForm1 = travelForm.get();
            expensesDto.setId(id);
            expensesDto.setApplyDate(travelForm1.getApplyDate());
            expensesDto.setProjectScope(travelForm1.getDescription());
            expensesDto.setPurposeOfVisit(travelForm1.getPurposeOfVisit().getPurposes());
            expensesDto.setColleagueDetails(travelForm1.getColleagueDetails());
            expensesDto.setTravelDate(travelForm1.getTravelDate());
            expensesDto.setProjectName(travelForm1.getProject().getProjectName());
            expensesDto.setNumberOfPeople(travelForm1.getNumberOfPeople());
            expensesDto.setManagerName(travelForm1.getManagers().getManagerName());
            List<ExpensesListDto> expensesListDto = new ArrayList<>();
            for (Expenses expenses : expensesList) {
                expensesListDto.add(convertToExpenseDto(expenses));
            }
            expensesDto.setExpensesList(expensesListDto);
        }


        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSES_FETCHED, expensesDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseAmountSpent() {
        List<Map<String, Object>> amountSpent = expensesRepoService.findAmountSpent();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.AMOUNT_SPENT_FETCHED, amountSpent));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpensesByRole(String role) {

        Optional<Roles> roles=rolesRepoService.findByRoleName(role);

        if(roles.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto(HttpStatus.NOT_FOUND,Constant.ROLE_NOT_EXIST,""));


        List<Expenses> expensesList=expensesRepoService.expensesByRole(roles.get().getId());

        List<ExpensesListDto> expensesListDto = new ArrayList<>();
        for (Expenses expenses : expensesList) {
            expensesListDto.add(convertToExpenseDto(expenses));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSES_FETCHED,expensesListDto));
    }
}

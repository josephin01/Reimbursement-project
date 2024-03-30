package com.reimbursement.project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.*;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Enum.NotificationFormType;
import com.reimbursement.project.entity.Enum.NotifyStatus;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.*;
import com.reimbursement.project.service.ExpenseClaimsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenseClaimsServiceImpl implements ExpenseClaimsService {

    private final EmployeeDetailsRepoService employeeDetailsRepoService;
    private final ExpenseClaimsRepoService expenseClaimsRepoService;
    private final ExpenseTypeRepoService expenseTypeRepoService;
    private final ManagersRepoService managersRepoService;
    private final ProjectsRepoService projectsRepoService;
    private final BillRepoService billRepoService;
    private final NotificationRepoService notificationRepoService;
    private final RolesRepoService rolesRepoService;

    @Override
    public ResponseEntity<ApiResponseDto> addExpenseClaims(ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException {

        ExpenseClaims expenseClaims = new ExpenseClaims();

        ExpenseType expenseType = expenseTypeRepoService.toFindById(expenseClaimsDto.getExpenseType())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_TYPE_NOT_FOUND));
        expenseClaims.setExpenseType(expenseType);

        EmployeeDetails employeeDetails = employeeDetailsRepoService.findByEmpId(expenseClaimsDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND +  expenseClaimsDto.getEmployeeId()));
        expenseClaims.setEmployeeDetails(employeeDetails);

        EmployeeDetails managerDetails = employeeDetailsRepoService.findByEmpId(expenseClaimsDto.getManagers())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND +  expenseClaimsDto.getManagers()));
        expenseClaims.setManagers(managerDetails.getManagers());

//        Managers managers = managersRepoService.findByManagerId(expenseClaimsDto.getManagers())
//                .orElseThrow(() -> new ResourceNotFoundException(Constant.MANAGER_NAME_NOT_FOUND));
//        expenseClaims.setManagers(managers);

        Projects projects = projectsRepoService.findByProjectId(expenseClaimsDto.getProjects())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND));
        expenseClaims.setProjects(projects);

        expenseClaims.setExpenseDescription(expenseClaimsDto.getExpenseDescription());
        expenseClaims.setApplyDate(expenseClaimsDto.getApplyDate());
        expenseClaims.setExpenseDate(expenseClaimsDto.getExpenseDate());

        ObjectMapper objectMapper = new ObjectMapper();
        String colleagueData = objectMapper.writeValueAsString(expenseClaimsDto.getColleague());
        expenseClaims.setColleagueDetails(colleagueData);

        List<Bills> bills = new ArrayList<>();
        for (BillsListDto billsListDto : expenseClaimsDto.getBillsDto()) {
            Bills bill = new Bills();
            bill.setBillName(billsListDto.getBillName());
            bill.setBillsUrl(billsListDto.getBillsUrl());
            bill.setBillType(billsListDto.getBillType());
            bill.setExpenseClaims(expenseClaims);
            bills.add(bill);
        }

        billRepoService.saveAll(bills);
        expenseClaims.setBills(bills);
        expenseClaims.setExpenseAmount(expenseClaimsDto.getExpenseAmount());

        Notification notification = new Notification();
        notification.setDate(new Date(System.currentTimeMillis()));
        notification.setEmployeeNotificationStatus(NotifyStatus.SENT);
        notification.setManagerNotificationStatus(NotifyStatus.SENT);
        notification.setAdminNotificationStatus(NotifyStatus.SENT);
        notification.setEmpId(employeeDetails.getEmpId());
        notification.setManagerId(managerDetails.getEmpId());
        notification.setNotificationFormType(NotificationFormType.EXPENSE_CLAIM);
        notification.setEmployeeAdminNotificationStatus(NotifyStatus.SENT);
        notificationRepoService.save(notification);

        expenseClaims.setNotification(notification);
        ExpenseClaims savedExpenseClaim = expenseClaimsRepoService.save(expenseClaims);
        ExpenseClaimResponseDto savedExpenseClaimDto = convertToDto(savedExpenseClaim);

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_ADDED, savedExpenseClaimDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseClaim(Long id) {
        ExpenseClaims expenseClaims = expenseClaimsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND));

        List<Bills> bills = billRepoService.findValidBills(id);
        List<BillsListDto> billsListDtos = bills.stream()
                .map(bill -> new BillsListDto(
                        bill.getId(),
                        bill.getBillName(),
                        bill.getBillsUrl(),
                        bill.getBillType()

           )).toList();

        ExpenseClaimResponseDto expenseClaimsDto = new ExpenseClaimResponseDto(
                expenseClaims.getExpenseType().getExpenses(),
                expenseClaims.getEmployeeDetails().getEmpId(),
                expenseClaims.getManagers().getManagerName(),
                expenseClaims.getProjects().getProjectName(),
                expenseClaims.getColleagueDetails(),
                expenseClaims.getExpenseDescription(),
                expenseClaims.getApplyDate(),
                expenseClaims.getExpenseDate(),
                billsListDtos,
                expenseClaims.getExpenseAmount()
        );

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_RETRIEVED, expenseClaimsDto));
    }


    @Transactional
    @Override
    public ResponseEntity<ApiResponseDto> updateExpenseClaim(Long id, ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException {
        ExpenseClaims expenseClaims = expenseClaimsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND));

        ExpenseType expenseType = expenseTypeRepoService.toFindById(expenseClaimsDto.getExpenseType())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_TYPE_NOT_FOUND));
        expenseClaims.setExpenseType(expenseType);

        EmployeeDetails employeeDetails = employeeDetailsRepoService.findByEmpId(expenseClaimsDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND));
        expenseClaims.setEmployeeDetails(employeeDetails);

        Managers managers = managersRepoService.findByManagerId(expenseClaimsDto.getManagers())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND));
        expenseClaims.setManagers(managers);

        Projects projects = projectsRepoService.findByProjectId(expenseClaimsDto.getProjects())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND));
        expenseClaims.setProjects(projects);

        expenseClaims.setExpenseDescription(expenseClaimsDto.getExpenseDescription());
        expenseClaims.setApplyDate(expenseClaimsDto.getApplyDate());
        expenseClaims.setExpenseDate(expenseClaimsDto.getExpenseDate());

        ObjectMapper objectMapper = new ObjectMapper();
        String colleagueData = objectMapper.writeValueAsString(expenseClaimsDto.getColleague());
        expenseClaims.setColleagueDetails(colleagueData);

        List<Bills> bills1 = billRepoService.findAllBills(id);
        billRepoService.deleteAllBills(bills1);

        List<Bills> bills = new ArrayList<>();
        for (BillsListDto billsDto: expenseClaimsDto.getBillsDto()){
            Bills bill = new Bills();
            bill.setBillName(billsDto.getBillName());
            bill.setBillsUrl(billsDto.getBillsUrl());
            bill.setBillType(billsDto.getBillType());
            bill.setExpenseClaims(expenseClaims);
            bills.add(bill);
        }

        billRepoService.saveAll(bills);
        expenseClaims.setBills(bills);
        expenseClaims.setExpenseAmount(expenseClaimsDto.getExpenseAmount());
        expenseClaims.setExpenseStatus(ExpenseStatus.PENDING);
        ExpenseClaims savedExpenseClaims = expenseClaimsRepoService.save(expenseClaims);
        ExpenseClaimResponseDto savedExpenseClaimDto = convertToDto(savedExpenseClaims);

        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_CLAIM_UPDATED, savedExpenseClaimDto));
    }

    private ExpenseClaimResponseDto convertToDto(ExpenseClaims savedExpenseClaim) {
        ExpenseClaims expenseClaims = expenseClaimsRepoService.findById(savedExpenseClaim.getId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND));

        List<Bills> bills = billRepoService.findValidBills(savedExpenseClaim.getId());
        List<BillsListDto> billsListDtos = bills.stream()
                .map(bill -> new BillsListDto(
                        bill.getId(),
                        bill.getBillName(),
                        bill.getBillsUrl(),
                        bill.getBillType()
                )).toList();

        return new ExpenseClaimResponseDto(
                expenseClaims.getExpenseType().getExpenses(),
                expenseClaims.getEmployeeDetails().getEmpId(),
                expenseClaims.getManagers().getManagerName(),
                expenseClaims.getProjects().getProjectName(),
                expenseClaims.getColleagueDetails(),
                expenseClaims.getExpenseDescription(),
                expenseClaims.getApplyDate(),
                expenseClaims.getExpenseDate(),
                billsListDtos,
                expenseClaims.getExpenseAmount()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseByStatus(String status) {
        ExpenseStatus statusEnum = ExpenseStatus.valueOf(status.toUpperCase());
        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findAllByExpenseStatus(statusEnum);

        if(!expenseClaims.isEmpty()){
            List<ExpenseClaimsListDto> expenseClaimsList = new ArrayList<>();
            for (ExpenseClaims eachClaims: expenseClaims){
                expenseClaimsList.add(getAllExpensesById(eachClaims.getId()));
            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_RETRIEVED, expenseClaimsList));
        }
        else {
            throw new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> expenseClaimApprove(ExpenseApproveDto expenseApproveDto) {
        ExpenseClaims expenseClaims = expenseClaimsRepoService.findById(expenseApproveDto.getExpenseClaimId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND));

        if (expenseClaims.getExpenseStatus().equals(ExpenseStatus.ADMIN_APPROVED)){
            throw new InvalidException(Constant.EXPENSE_CLAIM_ADMIN_ALREADY_APPROVED);
        }
        ExpenseStatus statusEnum = ExpenseStatus.valueOf(expenseApproveDto.getStatus().toUpperCase());
        expenseClaims.setExpenseStatus(statusEnum);
        expenseClaims.setRemarks(expenseApproveDto.getRemarks());
        expenseClaimsRepoService.save(expenseClaims);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_FORM_ADDED, null));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseClaimsCount() {

        List<ExpenseClaimCountDto> expenseCount = new ArrayList<>();
        for (ExpenseStatus status: ExpenseStatus.values()){
            ExpenseClaimCountDto expenseClaimCountDto = new ExpenseClaimCountDto();
            expenseClaimCountDto.setTitle(status.name());

            List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findByExpenseStatus(status);
            expenseClaimCountDto.setCount(expenseClaims.size());

            expenseCount.add(expenseClaimCountDto);
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_COUNT_RETRIEVED, expenseCount));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsByEmployee(Long empId, String status) {
        EmployeeDetails employeeDetails = employeeDetailsRepoService.findByEmpId(empId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND));

        if(employeeDetails==null){
            throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
        }

        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.findAllByEmployee(employeeDetails);
        if(expenseClaims.isEmpty()) {
            throw new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND);
        }

        if(status.equalsIgnoreCase(Constant.ALL)){
            List<ExpenseClaimsListDto> expenseClaimsList = new ArrayList<>();
            for (ExpenseClaims expenseClaim : expenseClaims){
                expenseClaimsList.add(getAllExpensesById(expenseClaim.getId()));
            }
            return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_RETRIEVED, expenseClaimsList));
        }
        else {
            List<ExpenseClaimsListDto> expenseClaimsList = expenseClaims.stream()
                    .filter(claim -> claim.getExpenseStatus().name().equalsIgnoreCase(status))
                    .map(claim -> new ExpenseClaimsListDto(
                            claim.getId(),
                            claim.getApplyDate(),
                            claim.getExpenseType().getExpenses(),
                            claim.getExpenseStatus().name(),
                            claim.getExpenseAmount()
                    )).toList();
            return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_RETRIEVED, expenseClaimsList));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdmin() {
        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.toFindAll();
        List<ExpenseClaimsListDataDto> expenseClaimsList = expenseClaims.stream()
                .map(claim -> new ExpenseClaimsListDataDto(
                        claim.getId(),
                        claim.getEmployeeDetails().getProfile(),
                        claim.getEmployeeDetails().getFirstName(),
                        claim.getApplyDate(),
                        claim.getProjects().getProjectName(),
                        claim.getExpenseStatus().name(),
                        claim.getExpenseAmount()
                )).toList();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_RETRIEVED, expenseClaimsList));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsForManager(Long managerId, String status) {
        EmployeeDetails managerDetails = employeeDetailsRepoService.findByEmpId(managerId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND));

        if(Boolean.FALSE.equals(managerDetails.getIsManager()))
            throw new InvalidException(Constant.NOT_MANAGER);

        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.getAllExpenseClaimsForManager(managerDetails.getManagers().getId());

        if(status.equalsIgnoreCase(Constant.ALL)){
            List<ExpenseClaimsListDataDto> expenseClaimsList = expenseClaims.stream()
                    .map(claims -> new ExpenseClaimsListDataDto(
                            claims.getId(),
                            claims.getEmployeeDetails().getProfile(),
                            claims.getEmployeeDetails().getFirstName(),
                            claims.getApplyDate(),
                            claims.getProjects().getProjectName(),
                            claims.getExpenseStatus().name(),
                            claims.getExpenseAmount()
                    )).toList();

            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.TEAM_EXPENSE_CLAIMS, expenseClaimsList));
        }
        else {
            List<ExpenseClaimsListDataDto> expenseClaimsList = expenseClaims.stream()
                    .filter(claims -> claims.getExpenseStatus().name().equalsIgnoreCase(status))
                    .map(claims -> new ExpenseClaimsListDataDto(
                            claims.getId(),
                            claims.getEmployeeDetails().getProfile(),
                            claims.getEmployeeDetails().getFirstName(),
                            claims.getApplyDate(),
                            claims.getProjects().getProjectName(),
                            claims.getExpenseStatus().name(),
                            claims.getExpenseAmount()
                    )).toList();

            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.TEAM_EXPENSE_CLAIMS, expenseClaimsList));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdminByRole(String role) {

        Optional<Roles> roles = rolesRepoService.findByRoleName(role);
        if (roles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto(HttpStatus.NOT_FOUND, Constant.ROLE_NOT_EXIST, ""));
        }
        List<ExpenseClaims> expenseClaims = expenseClaimsRepoService.getAllExpenseClaimsForAdmin(roles.get().getId());

        List<ExpenseClaimsListDataDto> expenseClaimsList = expenseClaims.stream()
                .map(claims -> new ExpenseClaimsListDataDto(
                        claims.getId(),
                        claims.getEmployeeDetails().getProfile(),
                        claims.getEmployeeDetails().getFirstName(),
                        claims.getApplyDate(),
                        claims.getProjects().getProjectName(),
                        claims.getExpenseStatus().name(),
                        claims.getExpenseAmount()
                )).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_CLAIMS_RETRIEVED, expenseClaimsList));
    }
    public ResponseEntity<ApiResponseDto> getExpenseClaimsCountByEmpId(Long empId, DashboardDto dashboardDto) {
        Boolean isTeam = dashboardDto.getIsTeam();
        Long employeeDetailsId;
        Long managerId;
        Optional<EmployeeDetails> employee = employeeDetailsRepoService.toFindByEmpId(empId);
        employeeDetailsId=employee.get().getId();
        TreeMap<ExpenseStatus,Integer> totalCount=new TreeMap<>();
        if(isTeam && employee.get().getIsManager()){
            managerId=managersRepoService.tofindManagerByEmpId(employeeDetailsId);
            for(ExpenseStatus expenseStatus:ExpenseStatus.values())
                totalCount.put(expenseStatus,expenseClaimsRepoService.getAllExpenseClaimsByManagerAndExpenseStatus(managerId,expenseStatus));
         }
        else {
            String roleName = employee.get().getRole().getRoleName();
            if(roleName.equals("Admin") && isTeam){
                for(ExpenseStatus expenseStatus:ExpenseStatus.values()){
                    totalCount.put(expenseStatus,expenseClaimsRepoService.findAllByExpenseStatus(expenseStatus).size());
                }
            }
            else {
                for (ExpenseStatus expenseStatus : ExpenseStatus.values())
                    totalCount.put(expenseStatus, expenseClaimsRepoService.findByEmpIdAndExpenseStatus(employeeDetailsId, expenseStatus));
            }
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_CLAIMS_COUNT_FETCHED,totalCount));
    }

    private ExpenseClaimsListDto getAllExpensesById(Long id) {

        ExpenseClaims expenseClaims = expenseClaimsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.EXPENSE_CLAIMS_NOT_FOUND));

        return new ExpenseClaimsListDto(
                expenseClaims.getId(),
                expenseClaims.getApplyDate(),
                expenseClaims.getExpenseType().getExpenses(),
                expenseClaims.getExpenseStatus().name(),
                expenseClaims.getExpenseAmount()
        );
    }

}

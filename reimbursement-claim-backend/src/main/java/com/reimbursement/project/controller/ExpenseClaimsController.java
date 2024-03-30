package com.reimbursement.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reimbursement.project.api.ExpenseClaimsApi;
import com.reimbursement.project.dto.DashboardDto;
import com.reimbursement.project.dto.ExpenseApproveDto;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseClaimsDto;
import com.reimbursement.project.service.ExpenseClaimsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExpenseClaimsController implements ExpenseClaimsApi {

    private final ExpenseClaimsService expenseClaimsService;

    @Override
    public ResponseEntity<ApiResponseDto> addExpenseClaims(ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException {
        return this.expenseClaimsService.addExpenseClaims(expenseClaimsDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseClaim(Long id){
        return this.expenseClaimsService.getExpenseClaim(id);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateExpenseClaim(Long id, ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException {
        return this.expenseClaimsService.updateExpenseClaim(id,expenseClaimsDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseByStatus(String status) {
        return this.expenseClaimsService.getExpenseByStatus(status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> expenseClaimApprove(ExpenseApproveDto expenseApproveDto) {
        return this.expenseClaimsService.expenseClaimApprove(expenseApproveDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseClaimsCount() {
        return expenseClaimsService.getExpenseClaimsCount();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsByEmployee(Long empId, String status) {
        return this.expenseClaimsService.getAllExpenseClaimsByEmployee(empId, status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdmin() {
        return expenseClaimsService.getAllExpenseClaimsForAdmin();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsForManager(Long managerId, String status) {
        return expenseClaimsService.getAllExpenseClaimsForManager(managerId, status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdminByRole(String role) {
        return expenseClaimsService.getAllExpenseClaimsForAdminByRole(role);
    }
    @Override
    public ResponseEntity<ApiResponseDto> getExpenseClaimsCountByEmployeeId(Long empId, DashboardDto dashboardDto) {
        return expenseClaimsService.getExpenseClaimsCountByEmpId(empId,dashboardDto);
    }

}

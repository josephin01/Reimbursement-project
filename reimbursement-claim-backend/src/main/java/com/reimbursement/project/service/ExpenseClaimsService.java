package com.reimbursement.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reimbursement.project.dto.DashboardDto;
import com.reimbursement.project.dto.ExpenseApproveDto;
import com.reimbursement.project.dto.ExpenseClaimsDto;
import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface ExpenseClaimsService {

    ResponseEntity<ApiResponseDto> addExpenseClaims(ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException;

    ResponseEntity<ApiResponseDto> getExpenseClaim(Long id);

    ResponseEntity<ApiResponseDto> updateExpenseClaim(Long id, ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException;

    ResponseEntity<ApiResponseDto> getExpenseByStatus(String status);

    ResponseEntity<ApiResponseDto> expenseClaimApprove(ExpenseApproveDto expenseApproveDto);

    ResponseEntity<ApiResponseDto> getExpenseClaimsCount();

    ResponseEntity<ApiResponseDto> getAllExpenseClaimsByEmployee(Long empId, String status);

    ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdmin();

    ResponseEntity<ApiResponseDto> getAllExpenseClaimsForManager(Long managerId, String status);

    ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdminByRole(String role);

    ResponseEntity<ApiResponseDto> getExpenseClaimsCountByEmpId(Long empId, DashboardDto dashboardDto);

}

package com.reimbursement.project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.DashboardDto;
import com.reimbursement.project.dto.ExpenseApproveDto;
import com.reimbursement.project.dto.ExpenseClaimsDto;
import com.reimbursement.project.dto.ApiResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.EXPENSE_CLAIMS)
public interface ExpenseClaimsApi {

    @PostMapping()
    ResponseEntity<ApiResponseDto> addExpenseClaims(@Valid @RequestBody ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException;

    @GetMapping(ApiConstant.EXPENSE_CLAIMS_ID)
    ResponseEntity<ApiResponseDto> getExpenseClaim(@PathVariable Long id);

    @PutMapping(ApiConstant.EXPENSE_CLAIMS_UPDATE)
    ResponseEntity<ApiResponseDto> updateExpenseClaim(@PathVariable Long id, @RequestBody ExpenseClaimsDto expenseClaimsDto) throws JsonProcessingException;

    @GetMapping(ApiConstant.EXPENSE_CLAIMS_ADMIN_VIEW_BY_STATUS)
    ResponseEntity<ApiResponseDto> getExpenseByStatus(@PathVariable String status);

    @PutMapping(ApiConstant.EXPENSE_CLAIMS_APPROVAL)
    ResponseEntity<ApiResponseDto> expenseClaimApprove(@RequestBody ExpenseApproveDto expenseApproveDto);

    @GetMapping(ApiConstant.EXPENSE_CLAIMS_COUNT)
    ResponseEntity<ApiResponseDto> getExpenseClaimsCount();

    @GetMapping(ApiConstant.EXPENSE_CLAIMS_HISTORY)
    ResponseEntity<ApiResponseDto> getAllExpenseClaimsByEmployee(@PathVariable Long empId, @PathVariable String status);

    @GetMapping(ApiConstant.EXPENSE_CLAIMS_ADMIN_VIEW_ALL)
    ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdmin();

    @GetMapping(ApiConstant.EXPENSE_CLAIMS_MANAGER_VIEW_BY_STATUS)
    ResponseEntity<ApiResponseDto> getAllExpenseClaimsForManager(@PathVariable Long managerId, @PathVariable String status);


    @GetMapping(ApiConstant.EXPENSE_CLAIMS_BY_ROLE)
    ResponseEntity<ApiResponseDto> getAllExpenseClaimsForAdminByRole(@PathVariable String role);

    @PostMapping(ApiConstant.EXPENSE_CLAIMS_EMPLOYEE_ID_COUNT)
    ResponseEntity<ApiResponseDto> getExpenseClaimsCountByEmployeeId(@PathVariable Long empId, @RequestBody DashboardDto dashboardDto);


}

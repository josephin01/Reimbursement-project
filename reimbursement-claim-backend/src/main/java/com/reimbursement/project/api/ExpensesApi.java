package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.EXPENSES)
@CrossOrigin
public interface ExpensesApi {

    @PostMapping
    ResponseEntity<ApiResponseDto> storeExpenses(@Valid @RequestBody ExpensesDto expensesDto);

    @GetMapping(ApiConstant.EXPENSE_ID)
    ResponseEntity<ApiResponseDto> getExpenses(@PathVariable Long id);

    @GetMapping(ApiConstant.TRAVEL_FORM_ID)
    ResponseEntity<ApiResponseDto> getExpensesByTravelForm(@PathVariable Long id);

    @PutMapping(ApiConstant.APPROVE_TRAVEL_FORM_ID)
    ResponseEntity<ApiResponseDto> updateExpenses(@PathVariable Long id, @Valid @RequestBody ExpenseIdDto expenseIdDto);

    @PostMapping(ApiConstant.EXPENSES_STATUS)
    ResponseEntity<ApiResponseDto> getAllExpenses(@RequestParam(name = "expenseStatus") String expenseStatus, @RequestBody RolesUpdateDto rolesDto);

    @GetMapping(ApiConstant.ALL_EXPENSE_STATUS)
    ResponseEntity<ApiResponseDto> getExpenseStatus();

    @GetMapping(ApiConstant.EXPENSES_AMOUNT_SPENT)
    ResponseEntity<ApiResponseDto> getExpenseAmountSpent();

    @PostMapping(ApiConstant.EXPENSE_COUNT_EMPLOYEE_ID)
     ResponseEntity<ApiResponseDto> getCount(@PathVariable Long empId, @Valid @RequestBody DashboardDto dashboardDto);

    @GetMapping(ApiConstant.EXPENSE_BY_ROLE)
    public ResponseEntity<ApiResponseDto> getExpensesByRole(@PathVariable String role);

}

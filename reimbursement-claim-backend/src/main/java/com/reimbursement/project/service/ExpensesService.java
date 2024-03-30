package com.reimbursement.project.service;

import com.reimbursement.project.dto.*;
import org.springframework.http.ResponseEntity;

public interface ExpensesService {

    public ResponseEntity<ApiResponseDto> storeExpenses(ExpensesDto expensesDto);

    public ResponseEntity<ApiResponseDto> getExpenses(Long id);

    public ResponseEntity<ApiResponseDto> updateExpenses(Long id, ExpenseIdDto expenseIdDto);

    ResponseEntity<ApiResponseDto> getExpenseStatus();
    public ResponseEntity<ApiResponseDto> getAllExpenses(String expenseStatus, RolesUpdateDto role);

    public ResponseEntity<ApiResponseDto> getExpensesCount(Long empId, DashboardDto dashboardDto);

    public ResponseEntity<ApiResponseDto> getExpensesById(Long id);

    ResponseEntity<ApiResponseDto> getExpenseAmountSpent();

    ResponseEntity<ApiResponseDto> getExpensesByRole(String role);
}

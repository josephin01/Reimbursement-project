package com.reimbursement.project.controller;

import com.reimbursement.project.api.ExpensesApi;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseIdDto;
import com.reimbursement.project.dto.ExpensesDto;
import com.reimbursement.project.service.ExpensesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExpensesController implements ExpensesApi {

    private final ExpensesService expensesService;

    @Override
    public ResponseEntity<ApiResponseDto> storeExpenses(ExpensesDto expensesDto) {
        return this.expensesService.storeExpenses(expensesDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenses(Long id) {
        return this.expensesService.getExpenses(id);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpensesByTravelForm(Long id) {
        return this.expensesService.getExpensesById(id);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateExpenses(Long id, ExpenseIdDto expenseIdDto) {
        return this.expensesService.updateExpenses(id, expenseIdDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenses(String expenseStatus, RolesUpdateDto role) {
        return this.expensesService.getAllExpenses(expenseStatus,role);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseStatus() {
        return expensesService.getExpenseStatus();
    }


    @Override
    public ResponseEntity<ApiResponseDto> getCount(Long empId, DashboardDto dashboardDto) {
        return this.expensesService.getExpensesCount(empId,dashboardDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpensesByRole(String role) {
        return expensesService.getExpensesByRole(role);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseAmountSpent() {
        return expensesService.getExpenseAmountSpent();
    }

}

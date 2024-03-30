package com.reimbursement.project.service;

import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseTypeDto;
import com.reimbursement.project.dto.ExpenseTypeIdDto;
import com.reimbursement.project.dto.FormTypeDto;
import org.springframework.http.ResponseEntity;

public interface ExpenseTypeService {
    ResponseEntity<ApiResponseDto> getExpenseType();

    ResponseEntity<ApiResponseDto> addExpenseType(ExpenseTypeDto expenseTypeDto);

    ResponseEntity<ApiResponseDto> editExpenseType(ExpenseTypeDto expenseTypeDto);

    ResponseEntity<ApiResponseDto> deleteExpenseType(ExpenseTypeIdDto expenseTypeIdDto);

    ResponseEntity<ApiResponseDto> getExpenseTypeByFormType(FormTypeDto formTypeDto);

    ResponseEntity<ApiResponseDto> getAllExpenseType();

    ResponseEntity<ApiResponseDto> getFormType();
}

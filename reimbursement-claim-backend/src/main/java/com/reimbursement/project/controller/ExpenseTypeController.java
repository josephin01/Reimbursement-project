package com.reimbursement.project.controller;

import com.reimbursement.project.api.ExpenseTypeApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseTypeDto;
import com.reimbursement.project.dto.ExpenseTypeIdDto;
import com.reimbursement.project.dto.FormTypeDto;
import com.reimbursement.project.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class ExpenseTypeController implements ExpenseTypeApi {


    private final ExpenseTypeService expenseTypeService;

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseType(){
            return expenseTypeService.getExpenseType();
    }

   @Override
    public ResponseEntity<ApiResponseDto> addExpenseType(ExpenseTypeDto expenseTypeDto){
        return expenseTypeService.addExpenseType(expenseTypeDto);
    }

   @Override
    public ResponseEntity<ApiResponseDto> editExpenseType(ExpenseTypeDto expenseTypeDto){
        return expenseTypeService.editExpenseType(expenseTypeDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteExpenseType(ExpenseTypeIdDto expenseTypeIdDto){
        return expenseTypeService.deleteExpenseType(expenseTypeIdDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getFormType() {
        return expenseTypeService.getFormType();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseTypeByFormType(FormTypeDto formTypeDto) {
        return expenseTypeService.getExpenseTypeByFormType(formTypeDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseType() {
        return expenseTypeService.getAllExpenseType();
    }
}
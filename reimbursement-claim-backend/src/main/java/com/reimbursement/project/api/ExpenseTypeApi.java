package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseTypeDto;
import com.reimbursement.project.dto.ExpenseTypeIdDto;
import com.reimbursement.project.dto.FormTypeDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.ADMIN)
public interface ExpenseTypeApi {

    @GetMapping(ApiConstant.EXPENSE_TYPE)
    ResponseEntity<ApiResponseDto> getExpenseType();

    @PostMapping(ApiConstant.EXPENSE_TYPE)
    ResponseEntity<ApiResponseDto> addExpenseType(@Valid @RequestBody ExpenseTypeDto expenseTypeDto);

    @PutMapping(ApiConstant.EXPENSE_TYPE)
    ResponseEntity<ApiResponseDto> editExpenseType(@Valid @RequestBody ExpenseTypeDto expenseTypeDto);

    @DeleteMapping(ApiConstant.EXPENSE_TYPE)
    ResponseEntity<ApiResponseDto> deleteExpenseType(@Valid @RequestBody ExpenseTypeIdDto expenseTypeIdDto);

    @GetMapping(ApiConstant.FORM_TYPE)
   ResponseEntity<ApiResponseDto> getFormType();

    @PostMapping(ApiConstant.EXPENSE_TYPE_FORM_TYPE)
    ResponseEntity<ApiResponseDto> getExpenseTypeByFormType(@Valid @RequestBody FormTypeDto formTypeDto);

    @GetMapping(ApiConstant.ALL_EXPENSE_TYPE)
    ResponseEntity<ApiResponseDto> getAllExpenseType();
}

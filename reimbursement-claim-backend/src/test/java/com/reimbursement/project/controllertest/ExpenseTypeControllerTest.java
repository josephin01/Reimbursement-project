package com.reimbursement.project.controllertest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.controller.ExpenseTypeController;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseTypeDto;
import com.reimbursement.project.dto.ExpenseTypeIdDto;
import com.reimbursement.project.dto.FormTypeDto;
import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.entity.ExpenseType;
import com.reimbursement.project.service.ExpenseTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExpenseTypeControllerTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    ExpenseTypeController expenseTypeController;
    @Mock
    ExpenseTypeService expenseTypeService;

    @Test
    void testGetExpenseType(){
        ExpenseType expenseType=new ExpenseType(1L,"Food Expense", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-04-20"),null,null);
        ExpenseTypeDto expenseTypeDto=new ExpenseTypeDto(1L,"Food Expense",FormType.EXPENSE_CLAIMS);
        List<ExpenseType> addExpense= new ArrayList<>();
        addExpense.add(expenseType);
        List<ExpenseTypeDto> expenseTypeDtos=new ArrayList<>();
        expenseTypeDtos.add(expenseTypeDto);

        ApiResponseDto apiResponseDto=new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_FETCHED,expenseTypeDtos);
        ResponseEntity<ApiResponseDto> expectedResult=new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.getExpenseType()).thenReturn(expectedResult);
        ResponseEntity<ApiResponseDto> actualResult= expenseTypeController.getExpenseType();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void testAddExpenseType(){

        ExpenseTypeDto expenseTypeDto=new ExpenseTypeDto(1L,"Food Expenses",FormType.EXPENSE_CLAIMS);
        ApiResponseDto apiResponseDto=new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_ADDED,expenseTypeDto);
        ResponseEntity<ApiResponseDto> expectedResult= new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.addExpenseType(expenseTypeDto)).thenReturn(expectedResult);
        ResponseEntity<ApiResponseDto> actualResult=expenseTypeController.addExpenseType(expenseTypeDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testEditExpenseType(){
        ExpenseTypeDto expenseTypeDto= new ExpenseTypeDto(1L,"Food Expenses",FormType.EXPENSE_CLAIMS);

        ApiResponseDto apiResponseDto=new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_UPDATED,expenseTypeDto);
        ResponseEntity<ApiResponseDto> expectedResult=new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.editExpenseType(expenseTypeDto)).thenReturn(expectedResult);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeController.editExpenseType(expenseTypeDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testDeleteExpenseType(){
        ExpenseTypeIdDto expenseTypeIdDto = new ExpenseTypeIdDto(1L);
        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_DELETED,null);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.deleteExpenseType(expenseTypeIdDto)).thenReturn(expectedResult);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeController.deleteExpenseType(expenseTypeIdDto);

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void testGetFormType(){

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK,Constant.FORM_TYPE_FETCHED,FormType.values());
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.getFormType()).thenReturn(expectedResult);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeController.getFormType();

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testGetExpenseTypeByFormType(){

        FormTypeDto formTypeDto = new FormTypeDto(FormType.EXPENSE_CLAIMS);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_FETCHED,new ExpenseTypeDto());
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.getExpenseTypeByFormType(formTypeDto)).thenReturn(expectedResult);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeController.getExpenseTypeByFormType(formTypeDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testGetAllExpenseType(){

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_FETCHED,new ExpenseType());
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(expenseTypeService.getAllExpenseType()).thenReturn(expectedResult);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeController.getAllExpenseType();

        assertEquals(expectedResult,actualResult);

    }


}

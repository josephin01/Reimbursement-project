package com.reimbursement.project.controllertest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.controller.ExpensesController;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.service.ExpensesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ExpensesControllerTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    ExpensesService expensesService;
    @InjectMocks
    ExpensesController expensesController;
    @Test
    void testStoreExpenses(){
        ExpensesDto expensesDto=new ExpensesDto(null,1L,"test",new Date(),"test",1,new Date(),"test","test","test",new ArrayList<>());
        when(expensesController.storeExpenses(expensesDto)).thenThrow(new ResourceNotFoundException(Constant.TRAVEL_FORM_NOT_EXISTS));
        assertThrows(ResourceNotFoundException.class,()->expensesController.storeExpenses(expensesDto));
    }
    @Test
    void testGetExpenses(){
        Long id=1L;
        when(expensesController.getExpenses(id)).thenThrow(new ResourceNotFoundException(Constant.EXPENSE_ID_NOT_FOUND));
        assertThrows(ResourceNotFoundException.class,()->expensesController.getExpenses(id));
    }
    @Test
    void testGetExpensesByTravelForm(){
        Long id=1L;
        when(expensesController.getExpensesByTravelForm(id)).thenThrow(new ResourceNotFoundException(Constant.NO_EXPENSES_TRAVEL_FORM));
        assertThrows(ResourceNotFoundException.class,()->expensesController.getExpensesByTravelForm(id));
    }
    @Test
    void testUpdateExpenses(){
        Long id=1L;
        ExpenseIdDto expenseIdDto=new ExpenseIdDto();
        when(expensesController.updateExpenses(id,expenseIdDto)).thenThrow(new ResourceNotFoundException(Constant.TRAVEL_FORM_ID_NOT_FOUND));
        assertThrows(ResourceNotFoundException.class,()->expensesController.updateExpenses(id,expenseIdDto));
    }
    @Test
    void  testGetAllExpenses(){
        String expenseStatus="ALL";
        RolesUpdateDto rolesUpdateDto=new RolesUpdateDto();
        when(expensesController.getAllExpenses(expenseStatus,rolesUpdateDto)).thenReturn(null);
        assertNull(expensesController.getAllExpenses(expenseStatus, rolesUpdateDto));
    }
    @Test
    void testGetExpenseStatus(){
        ExpenseStatus expenseStatus=ExpenseStatus.PENDING;
        when(expensesController.getExpenseStatus()).thenReturn(ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_STATUS_FETCHED, expenseStatus)));
        assertEquals(ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_STATUS_FETCHED, expenseStatus)),expensesController.getExpenseStatus());
    }
    @Test
    void testGetCount(){
        Long empId=1L;
        DashboardDto dashboardDto=new DashboardDto();
        when(expensesController.getCount(empId,dashboardDto)).thenThrow(new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND));
        assertThrows(ResourceNotFoundException.class,()->expensesController.getCount(empId,dashboardDto));
    }
    @Test
    void testGetExpenseAmountSpent(){
        List<Map<String, Object>> amountSpent=new ArrayList<>();
        when(expensesController.getExpenseAmountSpent()).thenReturn(ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.AMOUNT_SPENT_FETCHED, amountSpent)));
        assertEquals(ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.AMOUNT_SPENT_FETCHED, amountSpent)),expensesController.getExpenseAmountSpent());
    }
}

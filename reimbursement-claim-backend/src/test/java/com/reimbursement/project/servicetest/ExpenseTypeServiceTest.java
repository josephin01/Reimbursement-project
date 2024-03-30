package com.reimbursement.project.servicetest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ExpenseTypeDto;
import com.reimbursement.project.dto.ExpenseTypeIdDto;
import com.reimbursement.project.dto.FormTypeDto;
import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.entity.ExpenseType;
import com.reimbursement.project.repository.ExpenseTypeRepository;
import com.reimbursement.project.repository.service.ExpenseTypeRepoService;
import com.reimbursement.project.service.impl.ExpenseTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExpenseTypeServiceTest {
    @InjectMocks
    ExpenseTypeServiceImpl expenseTypeService;

    @Mock
    ExpenseTypeRepoService expenseTypeRepoService;

    @Mock
    ExpenseTypeRepository expenseTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExpenseType() {
        ExpenseType expenseType = new ExpenseType(1L, "Food Expense", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-04-20"), null, null);
        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto(1L, "Food Expense", FormType.EXPENSE_CLAIMS);
        List<ExpenseType> addExpense = new ArrayList<>();
        addExpense.add(expenseType);
        List<ExpenseTypeDto> expenseTypeDtos = new ArrayList<>();
        expenseTypeDtos.add(expenseTypeDto);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_FETCHED, expenseTypeDtos);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        when(expenseTypeRepoService.toFindAll()).thenReturn(addExpense);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.getExpenseType();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteExpenseType() {
        ExpenseTypeIdDto expenseTypeId = new ExpenseTypeIdDto();
        expenseTypeId.setId(1L);

        ExpenseType expenseType = new ExpenseType();

        when(expenseTypeRepoService.toFindById(expenseTypeId.getId())).thenReturn(Optional.of(new ExpenseType()));

        expenseType.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));

        when(expenseTypeRepoService.toSave(expenseType)).thenReturn(expenseType);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_DELETED, null);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.deleteExpenseType(expenseTypeId);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    void testDeletedExpenseType() {

        ExpenseType expenseType = new ExpenseType(1L, "Food Expense", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-04-20"), null, Date.valueOf("2024-03-12"));

        ExpenseTypeIdDto expenseTypeIdDto = new ExpenseTypeIdDto(1L);

        when(expenseTypeRepoService.toFindById(expenseTypeIdDto.getId())).thenReturn(Optional.of(expenseType));

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.BAD_REQUEST, Constant.EXPENSE_TYPE_NOT_FOUND, null);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.deleteExpenseType(expenseTypeIdDto);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    void testAddExpenseType() {

        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto();
        expenseTypeDto.setExpenses("Food Expenses");
        expenseTypeDto.setFormType(FormType.EXPENSE_CLAIMS);

        when(expenseTypeRepoService.isExpenseTypePresent(expenseTypeDto.getExpenses(), expenseTypeDto.getFormType())).thenReturn(Optional.of(new ExpenseType()));

        ExpenseType expenseType = new ExpenseType();
        expenseType.setExpenses(expenseTypeDto.getExpenses());
        expenseType.setFormType(expenseTypeDto.getFormType());

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_ADDED, expenseTypeDto);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        when(expenseTypeRepoService.toSave(expenseType)).thenReturn(expenseType);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.addExpenseType(expenseTypeDto);


        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testAddExistingExpenseType() {

        ExpenseType expenseType = new ExpenseType(1L, "Food expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null,null);
        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto();
        expenseTypeDto.setId(expenseType.getId());
        expenseTypeDto.setExpenses("Food expenses");
        expenseTypeDto.setFormType(FormType.EXPENSE_CLAIMS);


        when(expenseTypeRepoService.isExpenseTypePresent(expenseType.getExpenses(), expenseType.getFormType())).thenReturn(Optional.of(expenseType));
        when(expenseTypeRepoService.toSave(expenseType)).thenReturn(expenseType);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.CONFLICT, Constant.EXPENSE_TYPE_EXIST, expenseTypeDto);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.addExpenseType(expenseTypeDto);


        assertEquals(expectedResult, actualResult);

    }

    @Test
    void testGetFormType(){

        ApiResponseDto apiResponseDto=new ApiResponseDto(HttpStatus.OK,Constant.FORM_TYPE_FETCHED,FormType.values());
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.getFormType();

        assertEquals(expectedResult.getStatusCode(),actualResult.getStatusCode());
        assertEquals(expectedResult.getBody().getMessage(),actualResult.getBody().getMessage());
    }

    @Test
    void testRetrievedExpense() {
        ExpenseType expenseType = new ExpenseType(1L, "Food expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null, Date.valueOf("2024-03-09"));

        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto();
        expenseTypeDto.setId(expenseType.getId());
        expenseTypeDto.setFormType(FormType.EXPENSE_CLAIMS);
        expenseTypeDto.setExpenses(expenseType.getExpenses());

        String e = expenseTypeDto.getExpenses();
        FormType formType = expenseTypeDto.getFormType();
        when(expenseTypeRepoService.isExpenseTypePresent(e, formType)).thenReturn(Optional.of(expenseType));

        when(expenseTypeRepoService.toSave(expenseType)).thenReturn(expenseType);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.addExpenseType(expenseTypeDto);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK, Constant.RETRIEVED, expenseTypeDto);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetExpenseTypeByFormType(){

        FormTypeDto formTypeDto=new FormTypeDto(FormType.EXPENSE_CLAIMS);

        ExpenseType expenseType1 = new ExpenseType(1L, "Food expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null,null);
        ExpenseType expenseType2 = new ExpenseType(2L, "Travel expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null, null);
        ExpenseType expenseType3 = new ExpenseType(1L, "Food expenses", FormType.TRAVEL_FORM, Date.valueOf("2024-02-03"), null,null);

        List<ExpenseType> expenseTypesList = new ArrayList<>();
        expenseTypesList.add(expenseType1);
        expenseTypesList.add(expenseType2);

        List<ExpenseTypeDto> expenseTypes = new ArrayList<>();
        expenseTypes.add(new ExpenseTypeDto(expenseType1.getId(),expenseType1.getExpenses(),expenseType1.getFormType()));
        expenseTypes.add(new ExpenseTypeDto(expenseType2.getId(),expenseType2.getExpenses(),expenseType2.getFormType()));

        when(expenseTypeRepoService.getExpenseTypeByFormType(formTypeDto.getFormType())).thenReturn(expenseTypesList);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_FETCHED,expenseTypes);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.getExpenseTypeByFormType(formTypeDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testGetAllExpenseType(){
        ExpenseType expenseType1 = new ExpenseType(1L, "Food expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null,null);
        ExpenseType expenseType2 = new ExpenseType(2L, "Travel expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null, null);
        ExpenseType expenseType3 = new ExpenseType(1L, "Food expenses", FormType.TRAVEL_FORM, Date.valueOf("2024-02-03"), null,null);

        List<ExpenseType> expenseTypesList = new ArrayList<>();
        expenseTypesList.add(expenseType1);
        expenseTypesList.add(expenseType2);
        expenseTypesList.add(expenseType3);

        Set<String> expense = new TreeSet<>();
        expense.add(expenseType1.getExpenses());
        expense.add(expenseType2.getExpenses());
        expense.add(expenseType3.getExpenses());

        when(expenseTypeRepoService.getAllExpenseType()).thenReturn(expenseTypesList);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_FETCHED,expense);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.getAllExpenseType();

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testEditExpenseType(){

        ExpenseType expenseType = new ExpenseType(1L, "Food expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null,null);
        ExpenseTypeDto  expenseTypeDto = new ExpenseTypeDto(1L,"Food expenses",FormType.TRAVEL_FORM);

        ExpenseType editExpenseType = new ExpenseType();
        editExpenseType.setFormType(expenseTypeDto.getFormType());
        editExpenseType.setExpenses(expenseTypeDto.getExpenses());

        when(expenseTypeRepoService.toFindById(expenseTypeDto.getId())).thenReturn(Optional.of(expenseType));
        when(expenseTypeRepoService.toSave(editExpenseType)).thenReturn(editExpenseType);

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_UPDATED,expenseTypeDto),HttpStatus.OK);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.editExpenseType(expenseTypeDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testEditExistingExpenseType(){

        ExpenseType expenseType = new ExpenseType(1L, "Food expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null,null);
        ExpenseTypeDto  expenseTypeDto = new ExpenseTypeDto(1L,"Food expenses",FormType.EXPENSE_CLAIMS);

        ExpenseType editExpenseType = new ExpenseType();
        editExpenseType.setFormType(expenseTypeDto.getFormType());
        editExpenseType.setExpenses(expenseTypeDto.getExpenses());

        when(expenseTypeRepoService.toFindById(expenseTypeDto.getId())).thenReturn(Optional.of(expenseType));
        when(expenseTypeRepoService.isExpenseTypePresent(expenseTypeDto.getExpenses(),expenseTypeDto.getFormType())).thenReturn(Optional.of(expenseType));

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_EXIST,expenseTypeDto),HttpStatus.OK);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.editExpenseType(expenseTypeDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testNotUpdatedExpenseType(){

        ExpenseType expenseType = new ExpenseType(1L, "Travel expenses", FormType.EXPENSE_CLAIMS, Date.valueOf("2024-02-03"), null,null);
        ExpenseTypeDto  expenseTypeDto = new ExpenseTypeDto(2L,"Food expenses",FormType.EXPENSE_CLAIMS);

        when(expenseTypeRepoService.toFindById(expenseTypeDto.getId())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.BAD_REQUEST,Constant.EXPENSE_TYPE_NOT_UPDATED,null),HttpStatus.OK);
        ResponseEntity<ApiResponseDto> actualResult = expenseTypeService.editExpenseType(expenseTypeDto);

        assertEquals(expectedResult,actualResult);

    }


}

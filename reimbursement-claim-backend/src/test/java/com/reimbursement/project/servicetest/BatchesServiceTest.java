package com.reimbursement.project.servicetest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.*;
import com.reimbursement.project.entity.Enum.BillStatus;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.BatchesRepoService;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.ExpensesRepoService;
import com.reimbursement.project.repository.service.TravelFormRepoService;
import com.reimbursement.project.service.impl.BatchesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BatchesServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    BatchesServiceImpl batchesService;

    @Mock
    TravelFormRepoService travelFormRepoService;

    @Mock
    BatchesRepoService batchesRepoService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    EmployeeDetailsRepoService employeeDetailsRepoService;

    @Mock
    ExpensesRepoService expensesRepoService;


    @Test
    void testCreateBatchEmployeeEmpty() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpId(1L);
        BatchesRequestDto batchesRequestDto = new BatchesRequestDto(new Date(), new Date(), "test", employeeDetails);
        when(employeeDetailsRepoService.findByEmpId(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> batchesService.createBatch(batchesRequestDto));
    }

    @Test
    void testCreateBatchTravelFormEmpty() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpId(1L);
        employeeDetails.setFirstName("");
        BatchesRequestDto batchesRequestDto = new BatchesRequestDto(new Date(), new Date(), "test", employeeDetails);
        when(employeeDetailsRepoService.findByEmpId(1L)).thenReturn(Optional.of(employeeDetails));
        when(travelFormRepoService.filters(batchesRequestDto.getFromDate(), batchesRequestDto.getToDate(), batchesRequestDto.getEmployeeDetails().getFirstName(), batchesRequestDto.getEmployeeDetails().getEmpId())).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> batchesService.createBatch(batchesRequestDto));
    }

    @Test
    void testCreateBatchCheckListEmpty() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpId(1L);
        employeeDetails.setFirstName("");
        BatchesRequestDto batchesRequestDto = new BatchesRequestDto(new Date(), new Date(), "test", employeeDetails);
        when(employeeDetailsRepoService.findByEmpId(1L)).thenReturn(Optional.of(employeeDetails));
        TravelForm travelForm = new TravelForm();
        travelForm.setBatch(new Batches());
        travelForm.setExpenses(new ArrayList<>());
        List<TravelForm> travelFormList = new ArrayList<>();
        travelFormList.add(travelForm);
        when(travelFormRepoService.filters(batchesRequestDto.getFromDate(), batchesRequestDto.getToDate(), batchesRequestDto.getEmployeeDetails().getFirstName(), batchesRequestDto.getEmployeeDetails().getEmpId())).thenReturn(travelFormList);
        assertThrows(InvalidException.class, () -> batchesService.createBatch(batchesRequestDto));
    }

    @Test
    void testCreateBatchBatchEmpty() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpId(1L);
        employeeDetails.setFirstName("");
        BatchesRequestDto batchesRequestDto = new BatchesRequestDto(new Date(), new Date(), "test", employeeDetails);
        when(employeeDetailsRepoService.findByEmpId(1L)).thenReturn(Optional.of(employeeDetails));
        TravelForm travelForm = new TravelForm();
        Expenses expenses = new Expenses();
        expenses.setExpenseStatus(ExpenseStatus.ADMIN_APPROVED);
        List<Expenses> expensesList = new ArrayList<>();
        expensesList.add(expenses);
        travelForm.setExpenses(expensesList);
        List<TravelForm> travelFormList = new ArrayList<>();
        travelFormList.add(travelForm);
        when(travelFormRepoService.filters(batchesRequestDto.getFromDate(), batchesRequestDto.getToDate(), batchesRequestDto.getEmployeeDetails().getFirstName(), batchesRequestDto.getEmployeeDetails().getEmpId())).thenReturn(travelFormList);
        Batches batches = new Batches();
        batches.setId(1L);

        when(batchesRepoService.toSave(batches)).thenReturn(batches);
        when(batchesRepoService.toFindById(batches.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> batchesService.createBatch(batchesRequestDto));
    }

    @Test
    void testCreateBatch() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpId(1L);
        employeeDetails.setFirstName("");
        employeeDetails.setId(1L);
        BatchesRequestDto batchesRequestDto = new BatchesRequestDto(new Date(), new Date(), "test", employeeDetails);
        when(employeeDetailsRepoService.findByEmpId(employeeDetails.getEmpId())).thenReturn(Optional.of(employeeDetails));
        Expenses expenses = new Expenses();
        ExpenseType expenseType = new ExpenseType();
        expenseType.setId(1L);
        expenseType.setExpenses("Food expenses");
        expenses.setExpenseType(expenseType);
        expenses.setExpenseStatus(ExpenseStatus.ADMIN_APPROVED);
        List<Expenses> expensesList = new ArrayList<>();
        expensesList.add(expenses);
        TravelForm travelForm = new TravelForm(1L, new EmployeeDetails(), new Managers(), new Projects(), 1, new ArrayList<>(), new PurposeOfVisit(), "test", new Date(), new Date(), BillStatus.NO_BILL, "test", TravelFormStatus.FORM_APPROVED, "test", new Notification(), null, new Date(), new Date(), null);
        travelForm.setExpenses(expensesList);
        List<TravelForm> travelFormList = new ArrayList<>();
        travelFormList.add(travelForm);
        when(travelFormRepoService.filters(batchesRequestDto.getFromDate(), batchesRequestDto.getToDate(), batchesRequestDto.getEmployeeDetails().getFirstName(), batchesRequestDto.getEmployeeDetails().getEmpId())).thenReturn(travelFormList);
        Batches batches = new Batches();
        System.out.println("batchess---->" + batches);
        when(batchesRepoService.toSave(batches)).thenReturn(batches);
        when(batchesRepoService.toFindById(batches.getId())).thenReturn(Optional.of(batches));
        when(travelFormRepoService.toSave(travelForm)).thenReturn(travelForm);
        List<ExpensesListDto> expenseResponseDtoList = new ArrayList<>();
        ExpensesListDto expensesListDto = new ExpensesListDto();
        expensesListDto.setExpenseDate(expenses.getExpenseDate());
        when(modelMapper.map(expenses, ExpensesListDto.class)).thenReturn(expensesListDto);
        expenseResponseDtoList.add(expensesListDto);
        List<ExpensesDto> batchesFilterResponseDto = new ArrayList<>();
        ExpensesDto expensesDto = new ExpensesDto();
        expensesDto.setId(travelForm.getId());
        expensesDto.setPurposeOfVisit(travelForm.getPurposeOfVisit().getPurposes());
        expensesDto.setApplyDate(travelForm.getApplyDate());
        expensesDto.setColleagueDetails(travelForm.getColleagueDetails());
        expensesDto.setNumberOfPeople(travelForm.getNumberOfPeople());
        expensesDto.setTravelDate(travelForm.getTravelDate());
        expensesDto.setProjectScope(travelForm.getDescription());
        expensesDto.setProjectName(travelForm.getProject().getProjectName());
        expensesDto.setManagerName(travelForm.getManagers().getManagerName());
        expensesDto.setExpensesList(expenseResponseDtoList);
        batchesFilterResponseDto.add(expensesDto);
        ResponseEntity<ApiResponseDto> expected = ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.SPECIFIED_DATE_TRAVEL_FORM_FOUND, batchesFilterResponseDto));
        ResponseEntity<ApiResponseDto> actual = batchesService.createBatch(batchesRequestDto);
        assertEquals(expected, actual);

    }

    @Test
    void testGetBatchesPresent() {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setId(1L);
        employeeDetails.setEmpId(1L);
        employeeDetails.setFirstName("Emp");

        Batches batches = new Batches();
        batches.setId(1L);
        batches.setEmployeeDetails(employeeDetails);

        when(batchesRepoService.toFindAll()).thenReturn(List.of(batches));
        BatchesDto batchesDto = new BatchesDto();
        batchesDto.setEmpName(batches.getEmployeeDetails().getFirstName());
        batchesDto.setId(batches.getId());
        batchesDto.setEmpId(employeeDetails.getEmpId());

        List<BatchesDto> batchesDtos = new ArrayList<>();
        batchesDtos.add(batchesDto);

        when(modelMapper.map(batches, BatchesDto.class)).thenReturn(batchesDto);
        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK, Constant.BATCHES_FETCHED, batchesDtos);
        ResponseEntity<ApiResponseDto> expected = new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actual = batchesService.getBatches();

        assertEquals(expected, actual);

    }

    @Test
    void testGetBatchesEmpty() {
        when(batchesRepoService.toFindAll()).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> batchesService.getBatches());
    }

    @Test
    void testGetParticularBatchEmpty() {
        Long id = 1L;
        when((batchesRepoService.toFindById(id))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> batchesService.getParticularBatch(id));
    }

}

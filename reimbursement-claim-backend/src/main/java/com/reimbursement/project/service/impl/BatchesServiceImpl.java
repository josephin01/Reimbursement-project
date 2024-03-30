package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.Batches;
import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Expenses;
import com.reimbursement.project.entity.TravelForm;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.BatchesRepoService;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.ExpensesRepoService;
import com.reimbursement.project.repository.service.TravelFormRepoService;
import com.reimbursement.project.service.BatchesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchesServiceImpl implements BatchesService {
    private final TravelFormRepoService travelFormRepoService;

    private final BatchesRepoService batchesRepoService;

    private final ModelMapper modelMapper;

    private final EmployeeDetailsRepoService employeeDetailsRepoService;

    private final ExpensesRepoService expensesRepoService;

    @Override
    public ResponseEntity<ApiResponseDto> createBatch(BatchesRequestDto batchesRequestDto) {
        Long empId = batchesRequestDto.getEmployeeDetails().getEmpId();
        Date fromDate = batchesRequestDto.getFromDate();
        Date toDate = batchesRequestDto.getToDate();
        String firstName = batchesRequestDto.getEmployeeDetails().getFirstName();

        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmpId(empId);
        if(employeeDetails.isEmpty()){
            throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
        }
        List<TravelForm> travelForms = travelFormRepoService.filters(fromDate, toDate, firstName, empId);
        if (travelForms.isEmpty()) {
            throw new ResourceNotFoundException(Constant.SPECIFIED_DATE_TRAVEL_FORM_NOT_FOUND);
        }
        List<ExpensesDto> batchesFilterResponseDto = new ArrayList<>();
        List<TravelForm> checkTravelForms = new ArrayList<>();
        for (TravelForm travelForm : travelForms) {
            if (travelForm.getBatch() != null) {
                continue;
            }

            List<Expenses> checkExpenses = travelForm.getExpenses();
            if (!checkExpenses.isEmpty() && checkExpenses.get(0).getExpenseStatus().equals(ExpenseStatus.ADMIN_APPROVED)) {
                checkTravelForms.add(travelForm);
            }
        }
        if(checkTravelForms.isEmpty()){
             throw new InvalidException(Constant.TRAVEL_FORM_ALREADY_CLUBBED);
        }
            EmployeeDetails employeeDetails1 = employeeDetails.get();
            Batches batches = new Batches();
            batches.setFromDate(fromDate);
            batches.setToDate(toDate);
            batches.setEmployeeDetails(employeeDetails1);
            batches.setReference(batchesRequestDto.getReferences());
            Date date = new Date();
            batches.setDate(date);
            batches.setTravelForms(checkTravelForms);
            batchesRepoService.toSave(batches);
                   Optional<Batches> batches1 = batchesRepoService.toFindById(batches.getId());
                   if (batches1.isEmpty()) {
                       throw new ResourceNotFoundException(Constant.NO_BATCH_EXIST);
                   }
                   for(TravelForm travelForm:checkTravelForms) {
                       Batches batches2 = batches1.get();
                       travelForm.setBatch(batches2);
                       travelFormRepoService.toSave(travelForm);

                       ExpensesDto batchesFilterResponseDtoList = new ExpensesDto();
                       batchesFilterResponseDtoList.setId(travelForm.getId());
                       batchesFilterResponseDtoList.setColleagueDetails(travelForm.getColleagueDetails());
                       batchesFilterResponseDtoList.setManagerName(travelForm.getManagers().getManagerName());
                       batchesFilterResponseDtoList.setProjectScope(travelForm.getDescription());
                       batchesFilterResponseDtoList.setProjectName(travelForm.getProject().getProjectName());
                       batchesFilterResponseDtoList.setApplyDate(travelForm.getApplyDate());
                       batchesFilterResponseDtoList.setNumberOfPeople(travelForm.getNumberOfPeople());
                       batchesFilterResponseDtoList.setPurposeOfVisit(travelForm.getPurposeOfVisit().getPurposes());
                       batchesFilterResponseDtoList.setTravelDate(travelForm.getTravelDate());
                       List<Expenses> expenses = travelForm.getExpenses();
                       List<ExpensesListDto> expenseResponseDtoList = new ArrayList<>();
                       for (Expenses expenses1 : expenses) {
                           expenseResponseDtoList.add(convertToExpensesDto(expenses1));
                       }
                       batchesFilterResponseDtoList.setExpensesList(expenseResponseDtoList);
                       batchesFilterResponseDto.add(batchesFilterResponseDtoList);
                   }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.SPECIFIED_DATE_TRAVEL_FORM_FOUND, batchesFilterResponseDto));
    }
    private ExpensesListDto convertToExpensesDto(Expenses expenses1) {
        ExpensesListDto expenseResponseDto = modelMapper.map(expenses1, ExpensesListDto.class);
        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto();
        expenseTypeDto.setExpenses(expenses1.getExpenseType().getExpenses());
        expenseTypeDto.setId(expenses1.getExpenseType().getId());
        expenseResponseDto.setExpenseType(expenseTypeDto);
        return expenseResponseDto;
    }

    @Override
    public ResponseEntity<ApiResponseDto> getBatches() {
        List<Batches> batches = batchesRepoService.toFindAll();
        if (batches.isEmpty()) {
            throw new ResourceNotFoundException(Constant.NO_BATCH_EXIST);
        }
        List<BatchesDto> batchesDtoList = new ArrayList<>();
        for (Batches batches1 : batches) {
            batchesDtoList.add(convertToDto(batches1));
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.BATCHES_FETCHED, batchesDtoList));
    }

    private BatchesDto convertToDto(Batches batches1) {
        BatchesDto batchesDto = modelMapper.map(batches1, BatchesDto.class);
        Long empId = batches1.getEmployeeDetails().getEmpId();
        String firstname = batches1.getEmployeeDetails().getFirstName();
        batchesDto.setEmpId(empId);
        batchesDto.setEmpName(firstname);
        return batchesDto;
    }

    @Override
    public ResponseEntity<ApiResponseDto> getParticularBatch(Long id) {
        Optional<Batches> batches = batchesRepoService.toFindById(id);
        if (batches.isPresent()) {
            BatchInfoResponseDto batchInfoResponseDtos = new BatchInfoResponseDto();
            List<ExpensesDto> travelFormDtos = new ArrayList<>();
            List<TravelForm> travelForms = travelFormRepoService.findAllTravelFormsByBatchId(id);
            for (TravelForm travelForm : travelForms) {
                ExpensesDto travelFormDto = new ExpensesDto();
                travelFormDto.setTravelDate(travelForm.getTravelDate());
                travelFormDto.setPurposeOfVisit(travelForm.getPurposeOfVisit().getPurposes());
                travelFormDto.setId(travelForm.getId());
                travelFormDto.setColleagueDetails(travelForm.getColleagueDetails());
                travelFormDto.setProjectScope(travelForm.getDescription());
                travelFormDto.setApplyDate(travelForm.getApplyDate());
                travelFormDto.setNumberOfPeople(travelForm.getNumberOfPeople());
                travelFormDto.setProjectName(travelForm.getProject().getProjectName());
                travelFormDto.setManagerName(travelForm.getManagers().getManagerName());

                List<Expenses> expenses = travelForm.getExpenses();
                List<ExpensesListDto> expenseResponseDtoList = new ArrayList<>();
                for (Expenses expenses1 : expenses) {
                    expenseResponseDtoList.add(convertToExpensesDto(expenses1));
                }
                travelFormDto.setExpensesList(expenseResponseDtoList);
                travelFormDtos.add(travelFormDto);
            }
            batchInfoResponseDtos.setExpensesDto(travelFormDtos);

            String firstName = batches.get().getEmployeeDetails().getFirstName();
            Long empId = batches.get().getEmployeeDetails().getEmpId();
            BatchesDto batchesDto = modelMapper.map(batches, BatchesDto.class);
            batchesDto.setEmpName(firstName);
            batchesDto.setEmpId(empId);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.SPECIFIED_BATCH_ID_FETCHED, batchInfoResponseDtos));
        }
        throw new ResourceNotFoundException(Constant.NO_BATCH_EXIST);

    }
}


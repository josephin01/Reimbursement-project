package com.reimbursement.project.service.impl;
import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ExpenseTypeDto;
import com.reimbursement.project.dto.ExpenseTypeIdDto;
import com.reimbursement.project.dto.FormTypeDto;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.entity.ExpenseType;
import com.reimbursement.project.repository.service.ExpenseTypeRepoService;
import com.reimbursement.project.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepoService expenseTypeRepoService;

    @Override
    public ResponseEntity<ApiResponseDto> getExpenseType() {
        List<ExpenseType> expenses=expenseTypeRepoService.toFindAll();
        List<ExpenseTypeDto> expenseTypes=new ArrayList<>();
        for(ExpenseType expense:expenses){
            ExpenseTypeDto expenseTypeDto=ExpenseTypeDto.builder()
                    .id(expense.getId())
                    .expenses(expense.getExpenses())
                    .formType(expense.getFormType())
                    .build();
            expenseTypes.add(expenseTypeDto);
        }

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_FETCHED,expenseTypes));
    }

    @Override
    public ResponseEntity<ApiResponseDto> addExpenseType(ExpenseTypeDto expenseTypeDto) {

        String expenseType= StringUtils.capitalize(expenseTypeDto.getExpenses().toLowerCase());
        FormType formType=expenseTypeDto.getFormType();
        Optional<ExpenseType> expense=expenseTypeRepoService.isExpenseTypePresent(expenseType,formType);

        if(expense.isPresent()) {
           boolean checkDeleted=expense.get().getDeletedAt()!=null;

            if(checkDeleted){
                expense.get().setDeletedAt(null);
                expenseTypeRepoService.toSave(expense.get());
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.RETRIEVED,expenseTypeDto));
            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CONFLICT,Constant.EXPENSE_TYPE_EXIST,expenseTypeDto));
        }
        ExpenseType addexpenseType=new ExpenseType();
        addexpenseType.setExpenses(expenseType);
        addexpenseType.setFormType(formType);
        expenseTypeRepoService.toSave(addexpenseType);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_ADDED,expenseTypeDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> editExpenseType(ExpenseTypeDto expenseTypeDto) {
        String expenses=StringUtils.capitalize(expenseTypeDto.getExpenses().toLowerCase());
        FormType formType=expenseTypeDto.getFormType();
        Optional<ExpenseType> checkExpenseType=expenseTypeRepoService.toFindById(expenseTypeDto.getId());
        if(checkExpenseType.isPresent()) {
            Optional<ExpenseType> expense=expenseTypeRepoService.isExpenseTypePresent(expenses,formType);
            if(expense.isPresent()) {
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_EXIST, expenseTypeDto));
            }
                ExpenseType expenseType = checkExpenseType.get();
                expenseType.setFormType(formType);
                expenseType.setExpenses(expenses);
                expenseTypeRepoService.toSave(expenseType);
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.EXPENSE_TYPE_UPDATED, expenseTypeDto));
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST,Constant.EXPENSE_TYPE_NOT_UPDATED,null));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteExpenseType(ExpenseTypeIdDto expenseTypeIdDto) {
        Long id=expenseTypeIdDto.getId();
        ExpenseType expenseType=expenseTypeRepoService.toFindById(id).orElseThrow(null);
        if(expenseType.getDeletedAt()!=null) {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, Constant.EXPENSE_TYPE_NOT_FOUND, null));
        }
        expenseType.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        expenseTypeRepoService.toSave(expenseType);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_DELETED,null));
    }
    @Override
    public ResponseEntity<ApiResponseDto> getExpenseTypeByFormType(FormTypeDto formTypeDto) {
        List<ExpenseType> expenseTypes=expenseTypeRepoService.getExpenseTypeByFormType(formTypeDto.getFormType());
        List<ExpenseTypeDto> expenses=new ArrayList<>();
        for(ExpenseType expenseType:expenseTypes){
            ExpenseTypeDto expenseTypeDto=ExpenseTypeDto.builder()
                    .id(expenseType.getId())
                    .expenses(expenseType.getExpenses())
                    .formType(expenseType.getFormType())
                    .build();
            expenses.add(expenseTypeDto);
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_FETCHED,expenses));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllExpenseType() {
        List<ExpenseType> expenses= expenseTypeRepoService.getAllExpenseType();
        Set<String> expenseType=new TreeSet<>();
        for(ExpenseType expense:expenses){
            expenseType.add(expense.getExpenses());
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.EXPENSE_TYPE_FETCHED,expenseType));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getFormType() {
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.FORM_TYPE_FETCHED,FormType.values()));
    }
}

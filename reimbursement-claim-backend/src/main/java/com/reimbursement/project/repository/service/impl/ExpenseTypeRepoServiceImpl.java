package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.entity.ExpenseType;
import com.reimbursement.project.repository.ExpenseTypeRepository;
import com.reimbursement.project.repository.service.ExpenseTypeRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseTypeRepoServiceImpl implements ExpenseTypeRepoService {

    private final ExpenseTypeRepository expenseTypeRepository;
    @Override
    public List<ExpenseType> toFindAll() {
        return expenseTypeRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }


    @Override
    public Optional<ExpenseType> toFindById(Long id) {
        return expenseTypeRepository.findById(id);
    }

    @Override
    public ExpenseType toSave(ExpenseType expenseType) {
        return expenseTypeRepository.save(expenseType);
    }

    @Override
    public Optional<ExpenseType> isExpenseTypePresent(String expenses, FormType formType) {
        return expenseTypeRepository.findByExpensesAndFormType(expenses, formType);
    }
    @Override
    public List<ExpenseType> getExpenseTypeByFormType(FormType formType) {
       return expenseTypeRepository.findAllByFormTypeOrderByCreatedAtDesc(formType);
    }

    @Override
    public Optional<ExpenseType> checkExpenseType(String expenseType, FormType formType) {
        return expenseTypeRepository.findByExpensesAndFormType(expenseType, formType);

    }

    @Override
    public List<ExpenseType> getAllExpenseType() {
         return expenseTypeRepository.findAll();
    }

}

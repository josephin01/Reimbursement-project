package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.entity.ExpenseType;

import java.util.List;
import java.util.Optional;

public interface ExpenseTypeRepoService {

    List<ExpenseType> toFindAll();

    Optional<ExpenseType> toFindById(Long id);

    ExpenseType toSave(ExpenseType expenseType);

    Optional<ExpenseType> isExpenseTypePresent(String expenses, FormType formType);

    List<ExpenseType> getExpenseTypeByFormType(FormType formType);

    Optional<ExpenseType> checkExpenseType(String expenseType,FormType formType);

    List<ExpenseType> getAllExpenseType();
}

package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Expenses;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpensesRepoService {

    List<Map<String,Object>> expensesStatusCount(Long empId);

    List<Map<String,Object>> expensesCountByTravelFormId(List<Long> travelFormIds);

    List<Expenses> expensesByStatus(ExpenseStatus expenseStatus);

    Expenses toSave(Expenses expenses);

    List<Expenses> toSaveAll(List<Expenses> expensesList);

    Optional<Expenses> toFindById(Long id);

    List<Expenses> toFindAllByIdOrderByCreatedAtDesc(Long id);

    List<Expenses> toFindAll();

    List<Map<String, Object>> findAmountSpent();

    boolean findTravelFormInExpense(Long id);

    List<Expenses> expensesByRole(Long id);
}

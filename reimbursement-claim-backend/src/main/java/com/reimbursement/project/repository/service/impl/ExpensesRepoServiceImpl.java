package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Expenses;
import com.reimbursement.project.repository.ExpensesRepository;
import com.reimbursement.project.repository.service.ExpensesRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpensesRepoServiceImpl implements ExpensesRepoService {

    private final ExpensesRepository expensesRepository;

    @Override
    public List<Map<String, Object>> expensesStatusCount(Long empId) {
        return expensesRepository.getExpensesStatusCount(empId);
    }

    @Override
    public List<Map<String, Object>> expensesCountByTravelFormId(List<Long> travelFormIds) {
        return expensesRepository.getExpenseCountByStatus(travelFormIds);
    }

    @Override
    public List<Expenses> expensesByStatus(ExpenseStatus expenseStatus) {
        return expensesRepository.findByExpenseStatusOrderByCreatedAtDesc(expenseStatus);
    }

    @Override
    public Expenses toSave(Expenses expenses) {
        return expensesRepository.save(expenses);
    }

    @Override
    public List<Expenses> toSaveAll(List<Expenses> expensesList) {
        return expensesRepository.saveAll(expensesList);
    }

    @Override
    public Optional<Expenses> toFindById(Long id) {
        return expensesRepository.findById(id);
    }

    @Override
    public List<Expenses> toFindAllByIdOrderByCreatedAtDesc(Long id) {
        return expensesRepository.findAllByTravelFormIdOrderByCreatedAtDesc(id);
    }

    public List<Expenses> toFindAll() {
        return expensesRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> findAmountSpent() {
        return expensesRepository.findByExpenseAmount();
    }

    @Override
    public boolean findTravelFormInExpense(Long id) {
        return expensesRepository.existsByTravelForm_Id(id);
    }

    @Override
    public List<Expenses> expensesByRole(Long id) {
        return expensesRepository.findExpensesByRole(id);
    }

}

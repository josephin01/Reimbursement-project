package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.ExpenseClaims;

import java.util.List;
import java.util.Optional;

public interface ExpenseClaimsRepoService {

    List<ExpenseClaims> toFindAll();

    List<ExpenseClaims> findByExpenseId(Long expenseTypeId);
    ExpenseClaims save(ExpenseClaims expenseClaims);

    List<ExpenseClaims> findAllByExpenseStatus(ExpenseStatus statusEnum);

    Optional<ExpenseClaims> findById(Long id);

    List<ExpenseClaims> findAllByEmployee(EmployeeDetails employeeDetails);

    List<ExpenseClaims> findByExpenseStatus(ExpenseStatus expenseStatus);

    List<ExpenseClaims> getAllExpenseClaimsForManager(Long id);

    List<ExpenseClaims> getAllExpenseClaimsForAdmin(Long id);

    List<ExpenseClaims> findAllByManagerId(Long id);

    List<ExpenseClaims> findAllExpenseClaimNotification();
    Integer getAllExpenseClaimsByManagerAndExpenseStatus(Long managerId, ExpenseStatus expenseStatus);

    Integer findByEmpIdAndExpenseStatus(Long employeeDetailsId, ExpenseStatus expenseStatus);

}

package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Enum.NotifyStatus;
import com.reimbursement.project.entity.ExpenseClaims;
import com.reimbursement.project.repository.ExpenseClaimsRepository;
import com.reimbursement.project.repository.service.ExpenseClaimsRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseClaimsRepoServiceImpl implements ExpenseClaimsRepoService {

    private final ExpenseClaimsRepository expenseClaimsRepository;

    @Override
    public List<ExpenseClaims> toFindAll() {
        return expenseClaimsRepository.findAllByOrderByCreatedAtDesc();
    }
    public List<ExpenseClaims> findByExpenseId(Long expenseTypeId) {
        return expenseClaimsRepository.findAllByExpenseType_IdOrderByCreatedAtDesc(expenseTypeId);}

    public ExpenseClaims save(ExpenseClaims expenseClaims) {
        expenseClaimsRepository.save(expenseClaims);
        return expenseClaims;
    }
    @Override
    public List<ExpenseClaims> findAllByExpenseStatus(ExpenseStatus statusEnum) {
        return expenseClaimsRepository.findAllByExpenseStatusOrderByCreatedAtDesc(statusEnum);
    }
    @Override
    public Optional<ExpenseClaims> findById(Long id) {
        return expenseClaimsRepository.findById(id);
    }


    @Override
    public List<ExpenseClaims> findAllByEmployee(EmployeeDetails employee) {
        return expenseClaimsRepository.findAllByEmployeeDetailsOrderByCreatedAtDesc(employee);
    }

    @Override
    public List<ExpenseClaims> findByExpenseStatus(ExpenseStatus expenseStatus) {
        return expenseClaimsRepository.findAllByExpenseStatusOrderByCreatedAtDesc(expenseStatus);
    }

    @Override
    public List<ExpenseClaims> getAllExpenseClaimsForManager(Long managerId) {
        return expenseClaimsRepository.findAllByManagers_IdOrderByCreatedAtDesc(managerId);
    }

    @Override
    public List<ExpenseClaims> getAllExpenseClaimsForAdmin(Long id) {
        return expenseClaimsRepository.getAllExpenseClaimsForAdmin(id);
    }

    @Override
    public List<ExpenseClaims> findAllByManagerId(Long id) {
        return expenseClaimsRepository.getAllByManagers_IdAndNotification_ManagerNotificationStatusOrderByCreatedAtDesc(id, NotifyStatus.SENT);
    }

    @Override
    public List<ExpenseClaims> findAllExpenseClaimNotification() {
        return expenseClaimsRepository.getAllByNotification_AdminNotificationStatusOrderByCreatedAtDesc(NotifyStatus.SENT);
    }
    @Override
    public Integer getAllExpenseClaimsByManagerAndExpenseStatus(Long managerId, ExpenseStatus expenseStatus) {
        return expenseClaimsRepository.findAllByManagers_IdAndExpenseStatusOrderByCreatedAtDesc(managerId,expenseStatus).size();
    }

    @Override
    public Integer findByEmpIdAndExpenseStatus(Long employeeDetailsId, ExpenseStatus expenseStatus) {
        return expenseClaimsRepository.findAllByEmployeeDetails_IdAndExpenseStatusOrderByCreatedAtDesc(employeeDetailsId,expenseStatus).size();
    }


}

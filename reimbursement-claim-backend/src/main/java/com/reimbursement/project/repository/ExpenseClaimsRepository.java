package com.reimbursement.project.repository;

import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Enum.NotifyStatus;
import com.reimbursement.project.entity.ExpenseClaims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseClaimsRepository extends JpaRepository<ExpenseClaims,Long> {

    List<ExpenseClaims> findAllByExpenseType_Id(Long id);
    List<ExpenseClaims> findAllByExpenseType_IdOrderByCreatedAtDesc(Long id);
    List<ExpenseClaims> findAllByProjects_Id(Long id);
    List<ExpenseClaims> findAllByEmployeeDetails_EmpId(Long empId);
    List<ExpenseClaims> findAllByApplyDate(Date id);
    List<ExpenseClaims> findAllByExpenseAmount(Float amount);
    List<ExpenseClaims> findAllByExpenseAmountBetween(Float amount1, Float amt2);
    List<ExpenseClaims> findAllByExpenseStatusOrderByCreatedAtDesc(ExpenseStatus expense);
    List<ExpenseClaims> findAllByEmployeeDetails_Id(Long empId);
    List<ExpenseClaims> findAllByEmployeeDetailsOrderByCreatedAtDesc(EmployeeDetails employee);
    List<ExpenseClaims> findAllByManagers_IdOrderByCreatedAtDesc(Long managerId);
    List<ExpenseClaims> findAllByManagers_IdAndExpenseStatusOrderByCreatedAtDesc(Long managerId, ExpenseStatus expenseStatus);
    List<ExpenseClaims> findAllByEmployeeDetails_IdAndExpenseStatusOrderByCreatedAtDesc(Long empId, ExpenseStatus expenseStatus);
    List<ExpenseClaims> findAllByOrderByCreatedAtDesc();
    @Query("SELECT ec FROM ExpenseClaims ec WHERE ec.employeeDetails.id IN " +
            "(SELECT ed.id FROM EmployeeDetails ed JOIN ed.role r WHERE r.id = :id ORDER BY ed.createdAt)")
    List<ExpenseClaims> getAllExpenseClaimsForAdmin(Long id);
    List<ExpenseClaims> getAllByManagers_IdAndNotification_ManagerNotificationStatusOrderByCreatedAtDesc(Long id, NotifyStatus status);
    List<ExpenseClaims> getAllByNotification_AdminNotificationStatusOrderByCreatedAtDesc(NotifyStatus status);

}

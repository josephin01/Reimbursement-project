package com.reimbursement.project.repository;


import com.reimbursement.project.entity.Enum.ExpenseStatus;
import com.reimbursement.project.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findByExpenseStatusOrderByCreatedAtDesc(ExpenseStatus expenseStatus);

    @Query(value = "SELECT expenses.expense_status, COUNT(DISTINCT expenses.travel_form_id) as expense_status_count FROM expenses GROUP BY expenses.expense_status", nativeQuery = true)
    List<Map<String, Object>> getExpensesStatusCount(Long id);

    Expenses save(Expenses expenses);
    Optional<Expenses> findById(Long id);

    List<Expenses> findAllByTravelFormIdOrderByCreatedAtDesc(Long id);


    @Query(value = "SELECT expenses.expense_status as expense_status, COUNT(DISTINCT expenses.travel_form_id) as expense_status_count FROM expenses  WHERE expenses.travel_form_id IN :travelFormIds GROUP BY expenses.expense_status",nativeQuery = true)
    List<Map<String,Object>> getExpenseCountByStatus(List<Long> travelFormIds);

    @Query(value = "SELECT et.expenses AS expense, SUM(e.expense_amount) as amount_spent From expenses e RIGHT JOIN expense_type et ON et.id=e.expense_type_id GROUP BY et.expenses", nativeQuery = true)
    List<Map<String, Object>> findByExpenseAmount();

    Boolean existsByTravelForm_Id(Long id);


    @Query("SELECT e FROM Expenses e WHERE e.travelForm.id IN " +
            "(SELECT tf.id FROM TravelForm tf WHERE tf.employeeDetails.id IN " +
            "(SELECT ed.id FROM EmployeeDetails ed JOIN ed.role r WHERE r.id = :id ORDER BY ed.createdAt))")
    List<Expenses> findExpensesByRole(Long id);
}


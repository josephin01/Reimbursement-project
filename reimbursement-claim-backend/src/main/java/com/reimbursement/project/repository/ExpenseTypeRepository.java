package com.reimbursement.project.repository;

import com.reimbursement.project.entity.Enum.FormType;
import com.reimbursement.project.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType,Long> {
    Optional<ExpenseType> findById(Long id);
    List<ExpenseType> findByDeletedAtIsNullOrderByCreatedAtDesc();
     ExpenseType findByExpenses(String expenseType);
    Optional<ExpenseType> findByExpensesAndFormType(String expenses, FormType formType);
    List<ExpenseType> findAllByFormTypeOrderByCreatedAtDesc(FormType formType);


}

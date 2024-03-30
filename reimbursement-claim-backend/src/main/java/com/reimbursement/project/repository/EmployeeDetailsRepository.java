package com.reimbursement.project.repository;

import com.reimbursement.project.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {
    @Query("SELECT e.empId AS empId, e.firstName AS firstName, e.email AS email, e.profile AS profile FROM EmployeeDetails e")
    List<Map<String, Object>> findAllEmpIdAndNameAndProfileAndEmail();

    Optional<EmployeeDetails> findByEmail(String email);

    boolean existsByEmail(String userEmail);

    boolean existsByEmpId(Long empId);

    Optional<EmployeeDetails> findByEmpId(Long empId);

    List<EmployeeDetails> findByDeletedAtIsNullOrderByCreatedAtDesc();

    @Query("SELECT e.id as id, e.empId AS empId, e.firstName AS firstName, e.email AS email FROM EmployeeDetails e")
    List<Map<String, Object>> findAllEmpIdAndNameAndEmail();

    Optional<EmployeeDetails> findByManagers_Id(Long id);
}

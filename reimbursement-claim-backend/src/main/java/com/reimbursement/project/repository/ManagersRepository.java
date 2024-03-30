package com.reimbursement.project.repository;

import com.reimbursement.project.entity.Managers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagersRepository extends JpaRepository<Managers,Long> {

    List<Managers> findAllByDeletedAtIsNull();
    Managers findByEmployeeDetails_Id(Long EmpId);

    Optional<Managers> findById(Long id);
}

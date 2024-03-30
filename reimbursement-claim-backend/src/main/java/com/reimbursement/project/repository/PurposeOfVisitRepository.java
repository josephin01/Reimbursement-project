package com.reimbursement.project.repository;

import com.reimbursement.project.entity.PurposeOfVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurposeOfVisitRepository extends JpaRepository<PurposeOfVisit, Long> {
    List<PurposeOfVisit> findByDeletedAtIsNullOrderByCreatedAtDesc();
    Optional<PurposeOfVisit> findByPurposes(String purpose);
}

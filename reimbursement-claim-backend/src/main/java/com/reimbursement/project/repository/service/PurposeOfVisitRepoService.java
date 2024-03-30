package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.PurposeOfVisit;

import java.util.Optional;
import java.util.List;

public interface PurposeOfVisitRepoService {

    Optional<PurposeOfVisit> findPurposeById(Long id);

    List<PurposeOfVisit> toFindAll();

    PurposeOfVisit toSave(PurposeOfVisit purposeOfVisit);

    Optional<PurposeOfVisit> toFindById(Long id);

    Optional<PurposeOfVisit> isPurposePresent(String purpose);
}

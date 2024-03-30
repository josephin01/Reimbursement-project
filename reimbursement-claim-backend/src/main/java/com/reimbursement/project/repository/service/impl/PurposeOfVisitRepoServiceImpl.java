package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.PurposeOfVisit;
import com.reimbursement.project.repository.PurposeOfVisitRepository;
import com.reimbursement.project.repository.service.PurposeOfVisitRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurposeOfVisitRepoServiceImpl implements PurposeOfVisitRepoService {

    private final PurposeOfVisitRepository purposeOfVisitRepository;

    @Override
    public Optional<PurposeOfVisit> findPurposeById(Long id) {
        return purposeOfVisitRepository.findById(id);
    }
    public List<PurposeOfVisit> toFindAll() {
        return purposeOfVisitRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }


    @Override
    public PurposeOfVisit toSave(PurposeOfVisit purposeOfVisit) {
        return purposeOfVisitRepository.save(purposeOfVisit);
    }

    @Override
    public Optional<PurposeOfVisit> toFindById(Long id) {
        return purposeOfVisitRepository.findById(id);
    }

    @Override
    public Optional<PurposeOfVisit> isPurposePresent(String purpose) {
       return purposeOfVisitRepository.findByPurposes(purpose);
    }



}

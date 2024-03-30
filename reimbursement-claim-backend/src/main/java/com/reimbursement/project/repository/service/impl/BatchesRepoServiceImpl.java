package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Batches;
import com.reimbursement.project.repository.BatchesRepository;
import com.reimbursement.project.repository.service.BatchesRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchesRepoServiceImpl implements BatchesRepoService {

    private final BatchesRepository batchesRepository;

    @Override
    public Batches toSave(Batches batches) {
        return batchesRepository.save(batches);
    }

    @Override
    public Optional<Batches> toFindById(Long id) {
        return batchesRepository.findById(id);
    }

    @Override
    public List<Batches> toFindAll() {
        return batchesRepository.findAllByOrderByCreatedAtDesc();
    }
}

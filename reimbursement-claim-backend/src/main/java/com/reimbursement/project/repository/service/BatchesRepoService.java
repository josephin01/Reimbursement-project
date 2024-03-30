package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Batches;

import java.util.List;
import java.util.Optional;

public interface BatchesRepoService {

    Batches toSave(Batches batches);

    Optional<Batches> toFindById(Long id);

    List<Batches> toFindAll();
}

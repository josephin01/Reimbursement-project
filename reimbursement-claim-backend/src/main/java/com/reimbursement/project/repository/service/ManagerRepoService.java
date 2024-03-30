package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Managers;

import java.util.Optional;

public interface ManagerRepoService {
    Managers toSave(Managers managers);

    Optional<Managers> toFindById(Long id);

    Managers toFindByEmployeeId(Long empId);

    Optional<Managers> tofindByManagersId(Long id);
}

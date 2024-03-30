package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Managers;

import java.util.List;
import java.util.Optional;

public interface ManagersRepoService {
    
    Optional<Managers> findByManagerId(Long managerId);

    List<Managers> findAll();

    List<Managers> findManagersForGivenRole();
    Long tofindManagerByEmpId(Long employeeDetailsId);
}

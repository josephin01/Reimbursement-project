package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Managers;
import com.reimbursement.project.repository.ManagersRepository;
import com.reimbursement.project.repository.service.ManagersRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagersRepoServiceImpl implements ManagersRepoService {

    private final ManagersRepository managersRepository;

    @Override
    public Optional<Managers> findByManagerId(Long manager) {
        return managersRepository.findById(manager);
    }

    @Override
    public List<Managers> findAll() {
        return managersRepository.findAll();
    }

    @Override
    public List<Managers> findManagersForGivenRole() {
        return managersRepository.findAllByDeletedAtIsNull();
    }
    @Override
    public Long tofindManagerByEmpId(Long employeeDetailsId) {
        return managersRepository.findByEmployeeDetails_Id(employeeDetailsId).getId();
    }


}

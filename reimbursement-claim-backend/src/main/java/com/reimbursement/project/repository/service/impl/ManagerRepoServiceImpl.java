package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Managers;
import com.reimbursement.project.repository.ManagersRepository;
import com.reimbursement.project.repository.service.ManagerRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerRepoServiceImpl implements ManagerRepoService {
    private final ManagersRepository managersRepository;

    @Override
    public Managers toSave(Managers managers) {
        return managersRepository.save(managers);
    }

    @Override
    public Optional<Managers> toFindById(Long id) {
        return managersRepository.findById(id);
    }

    @Override
    public Managers toFindByEmployeeId(Long empId) {
        return managersRepository.findByEmployeeDetails_Id(empId);
    }

    @Override
    public Optional<Managers> tofindByManagersId(Long id) {
        return managersRepository.findById(id);
    }
}

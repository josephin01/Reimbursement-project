package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Roles;
import com.reimbursement.project.repository.RolesRepository;
import com.reimbursement.project.repository.service.RolesRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesRepoServiceImpl implements RolesRepoService {

    private final RolesRepository rolesRepository;

    @Override
    public boolean isRoleExist(String roleName) {
        return rolesRepository.existsByRoleName(roleName);
    }

    @Override
    public Roles toSave(Roles roles) {
        return rolesRepository.save(roles);
    }

    @Override
    public Optional<Roles> findByRoleName(String roleName) {
        return rolesRepository.findByRoleName(roleName);
    }

}

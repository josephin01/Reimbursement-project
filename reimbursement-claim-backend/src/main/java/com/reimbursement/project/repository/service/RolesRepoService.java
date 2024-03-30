package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Roles;

import java.util.Optional;

public interface RolesRepoService {
    boolean isRoleExist(String roleName);

    Roles toSave(Roles roles);

    Optional<Roles> findByRoleName(String roleName);

}

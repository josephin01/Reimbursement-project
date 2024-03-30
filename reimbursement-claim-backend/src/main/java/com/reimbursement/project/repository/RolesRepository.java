package com.reimbursement.project.repository;

import com.reimbursement.project.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    boolean existsByRoleName(String roleName);
    Roles save(Roles roles);

    Optional<Roles> findByRoleName(String roleName);

}

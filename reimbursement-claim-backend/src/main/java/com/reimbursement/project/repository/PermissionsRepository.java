package com.reimbursement.project.repository;

import com.reimbursement.project.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions,Long> {
}

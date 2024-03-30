package com.reimbursement.project.repository;

import com.reimbursement.project.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    Long findIdByProjectName(String projectName);
    Projects findByProjectName(String projectName);
}

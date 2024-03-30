package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Projects;

import java.util.List;
import java.util.Optional;

public interface ProjectsRepoService {
    Long getProjectId(String projectName);

    Optional<Projects> findByProjectId(Long projectId);

    Projects save(Projects projects);

    List<Projects> findAll();

    Optional<Projects> findById(Long id);

}

package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Projects;
import com.reimbursement.project.repository.ProjectsRepository;
import com.reimbursement.project.repository.service.ProjectsRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsRepoServiceImpl implements ProjectsRepoService {

    private final ProjectsRepository projectsRepository;
    @Override
    public Long getProjectId(String projectName) {
        return projectsRepository.findByProjectName(projectName).getId();
    }
    @Override
    public Optional<Projects> findByProjectId(Long projectId) {

        return projectsRepository.findById(projectId);
    }

    @Override
    public Projects save(Projects projects) {
        projectsRepository.save(projects);
        return projects;
    }

    @Override
    public List<Projects> findAll() {
        return projectsRepository.findAll();
    }

    @Override
    public Optional<Projects> findById(Long id) {
        return projectsRepository.findById(id);
    }

}


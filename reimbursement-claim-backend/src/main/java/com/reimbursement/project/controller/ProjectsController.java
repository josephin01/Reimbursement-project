package com.reimbursement.project.controller;

import com.reimbursement.project.api.ProjectsApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ProjectsDto;
import com.reimbursement.project.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectsController implements ProjectsApi {

    private final ProjectsService projectsService;

    @Override
    public ResponseEntity<ApiResponseDto> addNewProject(ProjectsDto projectsDto){
        return this.projectsService.addNewProject(projectsDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getProject(Long id){
        return this.projectsService.getProject(id);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllProjectsDropdown(){
        return this.projectsService.getAllProjectsDropdown();
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateProject(Long id, ProjectsDto projectsDto){
        return this.projectsService.updateProject(id, projectsDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> deleteProject(Long id) {
        return this.projectsService.deleteProject(id);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getProjectForReport() {
        return projectsService.getProjectForReport();
    }

}

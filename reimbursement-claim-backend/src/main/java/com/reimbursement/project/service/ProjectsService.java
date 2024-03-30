package com.reimbursement.project.service;

import com.reimbursement.project.dto.ProjectsDto;
import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface ProjectsService {

    ResponseEntity<ApiResponseDto> addNewProject(ProjectsDto projectsDto);

    ResponseEntity<ApiResponseDto> getProject(Long id);

    ResponseEntity<ApiResponseDto> getAllProjects();

    ResponseEntity<ApiResponseDto> getAllProjectsDropdown();

    ResponseEntity<ApiResponseDto> updateProject(Long id, ProjectsDto projectsDto);

    ResponseEntity<ApiResponseDto> deleteProject(Long id);

    ResponseEntity<ApiResponseDto> getProjectForReport();
}

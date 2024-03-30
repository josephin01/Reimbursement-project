package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ProjectsDto;
import com.reimbursement.project.dto.ApiResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.PROJECTS)
public interface ProjectsApi {

    @PostMapping
    ResponseEntity<ApiResponseDto> addNewProject(@Valid @RequestBody ProjectsDto projectsDto);

    @GetMapping(ApiConstant.PROJECTS_ID)
    ResponseEntity<ApiResponseDto> getProject(@PathVariable Long id);

    @GetMapping(ApiConstant.PROJECTS_ALL)
    ResponseEntity<ApiResponseDto> getAllProjects();

    @GetMapping(ApiConstant.PROJECTS_ALL_DROPDOWN)
    ResponseEntity<ApiResponseDto> getAllProjectsDropdown();

    @PutMapping(ApiConstant.PROJECTS_UPDATE)
    ResponseEntity<ApiResponseDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectsDto projectsDto);

    @DeleteMapping(ApiConstant.PROJECTS_DELETE)
    ResponseEntity<ApiResponseDto> deleteProject(@PathVariable Long id);

    @GetMapping(ApiConstant.PROJECTS_REPORT)
    ResponseEntity<ApiResponseDto> getProjectForReport();

}

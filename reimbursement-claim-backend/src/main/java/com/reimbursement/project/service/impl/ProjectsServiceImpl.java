package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ProjectsDto;
import com.reimbursement.project.dto.ProjectsListDto;
import com.reimbursement.project.dto.ProjectsResponseDto;
import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.entity.Managers;
import com.reimbursement.project.entity.Projects;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.ManagersRepoService;
import com.reimbursement.project.repository.service.ProjectsRepoService;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {

    private final ProjectsRepoService projectsRepoService;
    private final ManagersRepoService managersRepoService;
    private final EmployeeDetailsRepoService employeeDetailsRepoService;

    @Override
    public ResponseEntity<ApiResponseDto> addNewProject(ProjectsDto projectsDto) {
        Projects projects = new Projects();
        projects.setProjectName(projectsDto.getProjectName());
        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmpId(projectsDto.getManagers());
        System.out.println(projectsDto);
        System.out.println(employeeDetails);
        Managers managers = managersRepoService.findByManagerId(employeeDetails.get().getManagers().getId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND));
        projects.setManagers(managers);

        if(managers!=null){
            projectsRepoService.save(projects);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_ADDED, null));
        }
        else{
            throw new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getProject(Long id) {
        Projects project = projectsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND));

        if(project.getDeletedAt()!=null){
            throw new InvalidException(Constant.PROJECT_NAME_NOT_FOUND);
        }
        ProjectsResponseDto projectsDto = new ProjectsResponseDto(
                project.getId(),
                project.getProjectName(),
                project.getManagers().getManagerName(),
                project.getManagers().getEmployeeDetails().getEmpId()
        );
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_RETRIEVED, projectsDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllProjects() {
        List<Projects> projectsList = projectsRepoService.findAll();
        List<ProjectsResponseDto> projectsResponses = projectsList.stream()
                .filter(project -> project.getDeletedAt()==null)
                .map(project -> new ProjectsResponseDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getManagers().getManagerName(),
                        project.getManagers().getEmployeeDetails().getEmpId()
                )).toList();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_RETRIEVED, projectsResponses));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllProjectsDropdown() {
        List<Projects> projectsList = projectsRepoService.findAll();
        List<ProjectsListDto> projectsDtoList = new ArrayList<>();
        for (Projects projects: projectsList){
            if (projects.getDeletedAt()==null){
                projectsDtoList.add(getProjectById(projects.getId()));
            }
        }
        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_RETRIEVED, projectsDtoList));
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateProject(Long id, ProjectsDto projectsDto) {
        Projects projects = projectsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND));
        projects.setProjectName(projectsDto.getProjectName());

        Managers managers = managersRepoService.findByManagerId(projectsDto.getManagers())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND));
        projects.setManagers(managers);
        if(managers!=null){
            projectsRepoService.save(projects);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_INFO_UPDATED, null));
        }
        else {
            throw new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND);
        }
    }
    
    @Override
    public ResponseEntity<ApiResponseDto> deleteProject(Long id) {
        Projects projects = projectsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND));
        projects.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        projectsRepoService.save(projects);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_DELETED, null));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getProjectForReport() {
        List<Projects> projectsList = projectsRepoService.findAll();
        List<ProjectsResponseDto> projectsResponses = projectsList.stream()
                .map(project -> new ProjectsResponseDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getManagers().getManagerName(),
                        project.getManagers().getEmployeeDetails().getEmpId()
                )).toList();

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PROJECT_RETRIEVED, projectsResponses));
    }

    public ProjectsListDto getProjectById(Long id){
        Projects projects = projectsRepoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND));

        return new ProjectsListDto(
                projects.getId(),
                projects.getProjectName(),
                projects.getManagers().getId(),
                projects.getManagers().getManagerName()
        );
    }

}

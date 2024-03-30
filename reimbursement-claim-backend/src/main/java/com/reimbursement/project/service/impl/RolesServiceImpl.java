package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.RolesDto;
import com.reimbursement.project.dto.RolesUpdateDto;
import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.Role;
import com.reimbursement.project.entity.Managers;
import com.reimbursement.project.entity.Roles;
import com.reimbursement.project.exception.AlreadyExistException;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.ManagerRepoService;
import com.reimbursement.project.repository.service.RolesRepoService;
import com.reimbursement.project.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesRepoService rolesRepoService;

    private final EmployeeDetailsRepoService employeeDetailsRepoService;

    private final ManagerRepoService managerRepoService;

    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ApiResponseDto> postRoles(RolesDto roles) {
        if (rolesRepoService.isRoleExist(roles.getRoleName())) {
            throw new AlreadyExistException(Constant.ROLES_ALREADY_EXIST + roles.getRoleName());
        }
        Roles roles1 = modelMapper.map(roles, Roles.class);
        rolesRepoService.toSave(roles1);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_ADDED, roles));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getRoles() {
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_FETCHED, Role.values()));
    }

    public ResponseEntity<ApiResponseDto> updateRoles(RolesUpdateDto rolesUpdateDto) {
        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.toFindByEmpId(rolesUpdateDto.getEmpId());
        Optional<Roles> role = rolesRepoService.findByRoleName(rolesUpdateDto.getRoleName().toUpperCase());

        if (employeeDetails.isEmpty()) {
            throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND + rolesUpdateDto.getEmpId());

        } else {
            EmployeeDetails employeeDetails1 = employeeDetails.get();
            String roleName = rolesUpdateDto.getRoleName().toUpperCase();
            if (roleName.equals("MANAGER")) {
                if (Boolean.FALSE.equals(employeeDetails1.getIsManager())) {

                    if (role.isPresent()) {
                        employeeDetails1.setIsManager(true);
                        employeeDetails1.setRole(role.get());


                        Managers managers = managerRepoService.toFindByEmployeeId(employeeDetails1.getId());
                        if (managers == null) {
                            Managers managers1 = new Managers();
                            managers1.setManagerName(employeeDetails1.getFirstName() + " " + employeeDetails1.getLastName());
                            managers1.setEmployeeDetails(employeeDetails1);
                            employeeDetails1.setManagers(managers1);
                            managerRepoService.toSave(managers1);
                        } else if (managers.getDeletedAt() != null) {
                            managers.setDeletedAt(null);
                            managerRepoService.toSave(managers);
                        }
                        employeeDetailsRepoService.toSave(employeeDetails1);
                        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_UPDATED, null));

                    } else
                        throw new InvalidException(Constant.ROLE_NOT_EXIST);
                } else {
                    throw new InvalidException(Constant.ALREADY_MANAGER);

                }
            } else if (roleName.equals("EMPLOYEE")) {
                if (!employeeDetails1.getRole().getRoleName().equals("EMPLOYEE")) {
                    if (role.isPresent()) {
                        if (Boolean.TRUE.equals(employeeDetails1.getIsManager())) {
                            employeeDetails1.setRole(role.get());
                            employeeDetails1.setIsManager(false);
                            employeeDetailsRepoService.toSave(employeeDetails1);
                            Optional<Managers> managers = managerRepoService.tofindByManagersId(employeeDetails1.getManagers().getId());
                            if(managers.isEmpty()){
                                throw new InvalidException(Constant.NOT_MANAGER);
                            }
                            managers.get().setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
                            managerRepoService.toSave(managers.get());
                        } else if (employeeDetails1.getRole().getRoleName().equals("ADMIN")) {
                            employeeDetails1.setRole(role.get());
                            employeeDetailsRepoService.saveUserData(employeeDetails1);
                        }
                        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.ROLES_UPDATED, null));

                    } else {
                        throw new InvalidException(Constant.ROLE_NOT_EXIST);
                    }
                } else {
                    throw new InvalidException(Constant.ALREADY_EMPLOYEE);
                }

            }
        }
        return null;
    }
}
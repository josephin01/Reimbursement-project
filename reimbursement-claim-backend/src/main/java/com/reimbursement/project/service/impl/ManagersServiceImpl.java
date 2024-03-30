package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ManagersDto;
import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Managers;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.ManagersRepoService;
import com.reimbursement.project.service.ManagersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagersServiceImpl implements ManagersService {

    @Value("${secondaryManager}")
      Long secondaryManagerId;

    private final ManagersRepoService managersRepoService;
    private final EmployeeDetailsRepoService employeeDetailsRepoService;
    @Override
    public ResponseEntity<ApiResponseDto> getAllManagers() {
        List<Managers> managers = managersRepoService.findAll();
        List<ManagersDto> managersList = managers.stream()
                .map(manager -> new ManagersDto(
                        manager.getId(),
                        manager.getManagerName()
                )).toList();

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.MANAGERS_RETRIEVED, managersList));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getManagerByRole(String roles) {

        if(roles.equals(Constant.MANAGER) || roles.equals(Constant.ADMIN)){
            Managers manager = managersRepoService.findByManagerId(secondaryManagerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            ManagersDto managersDto = ManagersDto.builder()
                    .managerId(manager.getEmployeeDetails().getEmpId())
                    .managerName(manager.getManagerName())
                    .build();
            List<ManagersDto> managerResponse = new ArrayList<>();
            managerResponse.add(managersDto);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.MANAGERS_RETRIEVED, managerResponse));
        }
        List<Managers> managers = managersRepoService.findManagersForGivenRole();
        List<ManagersDto> managersDtoList = new ArrayList<>();
        for(Managers managersData : managers){
            if(!Objects.equals(managersData.getId(), secondaryManagerId)) {
                ManagersDto managersDto = ManagersDto.builder()
                        .managerId(managersData.getEmployeeDetails().getEmpId())
                        .managerName(managersData.getManagerName())
                        .build();
                managersDtoList.add(managersDto);
            }
        }

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.MANAGERS_RETRIEVED, managersDtoList));
    }

}

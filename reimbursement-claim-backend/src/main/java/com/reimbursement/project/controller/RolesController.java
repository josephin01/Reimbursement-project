package com.reimbursement.project.controller;

import com.reimbursement.project.api.RolesApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.RolesDto;
import com.reimbursement.project.dto.RolesUpdateDto;

import com.reimbursement.project.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RolesController implements RolesApi {

    private final RolesService rolesService;
    @Override
    public ResponseEntity<ApiResponseDto> postRoles(RolesDto rolesDto) {
        return rolesService.postRoles(rolesDto);
    }
    @Override
    public ResponseEntity<ApiResponseDto> getRoles() { return rolesService.getRoles();}
    public ResponseEntity<ApiResponseDto> updateRoles(RolesUpdateDto rolesUpdateDto) {
        return rolesService.updateRoles(rolesUpdateDto);
    }
}

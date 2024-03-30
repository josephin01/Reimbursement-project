package com.reimbursement.project.service;

import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.RolesDto;

import com.reimbursement.project.dto.RolesUpdateDto;
import org.springframework.http.ResponseEntity;

public interface RolesService {

    public ResponseEntity<ApiResponseDto> postRoles(RolesDto rolesDto);
    ResponseEntity<ApiResponseDto> getRoles();
    public ResponseEntity<ApiResponseDto> updateRoles(RolesUpdateDto rolesUpdateDto);
}

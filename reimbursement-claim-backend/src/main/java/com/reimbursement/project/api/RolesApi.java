package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.RolesDto;

import com.reimbursement.project.dto.RolesUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.ROLES)
public interface RolesApi {
    @GetMapping
    ResponseEntity<ApiResponseDto> getRoles();

    @PostMapping
    ResponseEntity<ApiResponseDto> postRoles(@Valid @RequestBody RolesDto rolesDto);

    @PutMapping
    ResponseEntity<ApiResponseDto> updateRoles(@Valid @RequestBody RolesUpdateDto rolesUpdateDto);
}

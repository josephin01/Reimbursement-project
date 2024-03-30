package com.reimbursement.project.service;

import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface ManagersService {
    
    ResponseEntity<ApiResponseDto> getAllManagers();

    ResponseEntity<ApiResponseDto> getManagerByRole(String roles);
}

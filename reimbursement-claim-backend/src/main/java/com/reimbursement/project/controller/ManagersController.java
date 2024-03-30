package com.reimbursement.project.controller;

import com.reimbursement.project.api.ManagersApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.service.ManagersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagersController implements ManagersApi {

    private final ManagersService managersService;

    @Override
    public ResponseEntity<ApiResponseDto> getAllManagers() {
        return managersService.getAllManagers();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getManagerByRole(String roles) {
        return managersService.getManagerByRole(roles);
    }

}

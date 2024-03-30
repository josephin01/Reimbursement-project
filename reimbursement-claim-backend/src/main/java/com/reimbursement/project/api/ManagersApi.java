package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiConstant.MANAGER)
@CrossOrigin
public interface ManagersApi {

    @GetMapping()
    ResponseEntity<ApiResponseDto> getAllManagers();

    @GetMapping(ApiConstant.MANAGER_ROLES)
    ResponseEntity<ApiResponseDto> getManagerByRole(@PathVariable String roles);
}

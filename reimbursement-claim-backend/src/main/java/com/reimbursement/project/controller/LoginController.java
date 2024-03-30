package com.reimbursement.project.controller;


import com.reimbursement.project.api.LoginApi;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.service.EmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginApi {

    private final EmployeeDetailsService employeeDetailsService;

    @Override
    public ResponseEntity<ApiResponseDto> getUserIfExists(LoginRequestDto token) {
        return employeeDetailsService.getUserIfExists(token);
    }

    @Override
    public ResponseEntity<ApiResponseDto> addUserDetails(UserDataDto userData) {
        return employeeDetailsService.addUser(userData);
    }

    @Override
    public ResponseEntity<ApiResponseDto> regenerateToken(RegenerateTokenRequestDto regenerateTokenDto) {
        return employeeDetailsService.generateToken(regenerateTokenDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> logoutUser(LogoutRequestDto logoutData) {
        return employeeDetailsService.logoutUser(logoutData);
    }

}

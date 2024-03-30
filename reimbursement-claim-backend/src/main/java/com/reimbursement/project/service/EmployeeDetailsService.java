package com.reimbursement.project.service;

import com.reimbursement.project.dto.*;
import org.springframework.http.ResponseEntity;

public interface EmployeeDetailsService {
    public ResponseEntity<ApiResponseDto> getColleaguesInfo();
    ResponseEntity<ApiResponseDto> getUserIfExists(LoginRequestDto token);
    ResponseEntity<ApiResponseDto> addUser(UserDataDto userData);
    ResponseEntity<ApiResponseDto> generateToken(RegenerateTokenRequestDto regenerateTokenRequestDto);
    ResponseEntity<ApiResponseDto> logoutUser(LogoutRequestDto logoutData);
    ResponseEntity<ApiResponseDto> getAllEmployees();
    ResponseEntity<ApiResponseDto> getEmployeeById(Long empId);
    ResponseEntity<ApiResponseDto> deleteEmployeeById(Long empId);
    ResponseEntity<ApiResponseDto> searchEmployees();
    ResponseEntity<ApiResponseDto> getAllEmployeesDetails();
    ResponseEntity<ApiResponseDto> getEmployeeIdAndName();
}

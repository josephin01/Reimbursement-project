package com.reimbursement.project.controller;

import com.reimbursement.project.api.EmployeeDetailsApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.service.EmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeDetailsController implements EmployeeDetailsApi {

    private final EmployeeDetailsService employeeDetailsService;
    @Override
    public ResponseEntity<ApiResponseDto> getColleagues() {
        return employeeDetailsService.getColleaguesInfo();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllEmployeeDetails() {
        return employeeDetailsService.getAllEmployeesDetails();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllEmployees() {
        return employeeDetailsService.getAllEmployees();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getEmployeeById(Long empId) {
        return employeeDetailsService.getEmployeeById(empId);
    }
    @Override
    public ResponseEntity<ApiResponseDto> deleteEmployeeById(Long empId) {
        return employeeDetailsService.deleteEmployeeById(empId);
    }

    @Override
    public ResponseEntity<ApiResponseDto> searchEmployees() {
        return employeeDetailsService.searchEmployees();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getEmployeeIdAndName() {
        return employeeDetailsService.getEmployeeIdAndName();
    }

}

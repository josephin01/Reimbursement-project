package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.EMPLOYEES)
public interface EmployeeDetailsApi {

    @GetMapping(ApiConstant.COLLEAGUES)
    ResponseEntity<ApiResponseDto> getColleagues();

    @GetMapping(ApiConstant.ALL_EMPLOYEE)
    ResponseEntity<ApiResponseDto> getAllEmployeeDetails();

    @GetMapping(ApiConstant.EMPLOYEE_ALL)
    ResponseEntity<ApiResponseDto> getAllEmployees();

    @GetMapping(ApiConstant.BATCH_EMPLOYEE_ID)
    ResponseEntity<ApiResponseDto> getEmployeeById(@PathVariable Long empId);

    @PutMapping(ApiConstant.BATCH_DELETE_EMPLOYEE_ID)
    ResponseEntity<ApiResponseDto> deleteEmployeeById(@PathVariable Long empId);

    @GetMapping(ApiConstant.BATCH_SEARCH)
    ResponseEntity<ApiResponseDto> searchEmployees();

    @GetMapping(ApiConstant.EMPLOYEE_ID_NAME)
    ResponseEntity<ApiResponseDto> getEmployeeIdAndName();
}

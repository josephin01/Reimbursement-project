package com.reimbursement.project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;

import com.reimbursement.project.dto.StatusUpdateDto;
import com.reimbursement.project.dto.TravelFormsDto;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.TRAVEL_FORM)
public interface TravelFormApi {
    @GetMapping(ApiConstant.BILLS_COUNT)
    ResponseEntity<ApiResponseDto> getBillsStatusCount();

    @GetMapping(ApiConstant.TRAVEL_FORM_COUNT)
    ResponseEntity<ApiResponseDto> getTravelFormCount();
    @PostMapping()
    ResponseEntity<ApiResponseDto> addTravelForm(@Valid @RequestBody TravelFormsDto travelFormData) throws JsonProcessingException;

    @GetMapping(ApiConstant.TRAVEL_FORM_GET)
    ResponseEntity<ApiResponseDto> getTravelForm(@PathVariable Long travelFormId);

    @GetMapping(ApiConstant.TRAVEL_FORM_EMPLOYEE_BY_STATUS)
    ResponseEntity<ApiResponseDto> getAllTravelFormByEmployeeId(@PathVariable Long employeeId, @PathVariable String status);

    @GetMapping(ApiConstant.TRAVEL_FORM_MANAGER_BY_STATUS)
    ResponseEntity<ApiResponseDto> getAllTravelFormByManagerId(@PathVariable Long managerId,@PathVariable String status);

    @GetMapping(ApiConstant.TRAVEL_FORM_ADMIN_BY_STATUS)
    ResponseEntity<ApiResponseDto> getAllTravelForm(@PathVariable String status);

    @PutMapping(ApiConstant.TRAVEL_FORM_UPDATE_STATUS)
    ResponseEntity<ApiResponseDto> updateTravelFormStatus(@Valid @RequestBody StatusUpdateDto statusUpdateDto,@PathVariable Long travelFormId);

    @GetMapping(ApiConstant.TRAVEL_FORM_BY_STATUS)
    ResponseEntity<ApiResponseDto> getTravelFormByStatus(@PathVariable String status);

    @GetMapping(ApiConstant.TRAVEL_FORM_ADMIN)
    ResponseEntity<ApiResponseDto> getTravelFormForAdmin(@PathVariable String role);

}

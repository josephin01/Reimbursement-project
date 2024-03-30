package com.reimbursement.project.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.StatusUpdateDto;
import com.reimbursement.project.dto.TravelFormsDto;
import org.springframework.http.ResponseEntity;

public interface TravelFormService {

    ResponseEntity<ApiResponseDto> getBillsStatusCount();

    ResponseEntity<ApiResponseDto> getTravelFormStatusCount();

    ResponseEntity<ApiResponseDto> addFormDetails(TravelFormsDto travelFormData) throws JsonProcessingException;

    ResponseEntity<ApiResponseDto> getTravelFormData(Long travelFormId);

    ResponseEntity<ApiResponseDto> getAllTravelForms(Long employeeId, String travelFormStatus);

    ResponseEntity<ApiResponseDto> getAllTravelFormForManager(Long managerId,String status);

    ResponseEntity<ApiResponseDto> getAllTravelFormsForAdmin(String status);

    ResponseEntity<ApiResponseDto> updateStatus(StatusUpdateDto statusUpdateDto, Long travelFormId);
    ResponseEntity<ApiResponseDto> getTravelFormByStatus(String status);

    ResponseEntity<ApiResponseDto> getTravelFormByRole(String role);
}

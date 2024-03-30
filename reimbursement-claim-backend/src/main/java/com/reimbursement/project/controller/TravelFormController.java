package com.reimbursement.project.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.reimbursement.project.api.TravelFormApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.StatusUpdateDto;
import com.reimbursement.project.dto.TravelFormsDto;
import com.reimbursement.project.service.TravelFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TravelFormController implements TravelFormApi {

    private final TravelFormService travelFormService;

    @Override
    public ResponseEntity<ApiResponseDto> getBillsStatusCount() {
        return travelFormService.getBillsStatusCount();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormCount() {
        return travelFormService.getTravelFormStatusCount();
    }

    @Override
    public ResponseEntity<ApiResponseDto> addTravelForm(TravelFormsDto travelFormData) throws
            JsonProcessingException {
        return travelFormService.addFormDetails(travelFormData);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getTravelForm(Long travelFormId) {
        return travelFormService.getTravelFormData(travelFormId);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllTravelFormByEmployeeId(Long employeeId, String status) {
        return travelFormService.getAllTravelForms(employeeId,status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllTravelFormByManagerId(Long managerId,String status) {
        return travelFormService.getAllTravelFormForManager(managerId,status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllTravelForm(String status) {
        return travelFormService.getAllTravelFormsForAdmin(status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateTravelFormStatus(StatusUpdateDto statusUpdateDto, Long travelFormId) {
        return travelFormService.updateStatus(statusUpdateDto, travelFormId);

    }

    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormByStatus(String status) {
        return this.travelFormService.getTravelFormByStatus(status);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormForAdmin(String role) {
        return travelFormService.getTravelFormByRole(role);
    }

}

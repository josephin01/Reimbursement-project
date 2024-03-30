package com.reimbursement.project.controller;

import com.reimbursement.project.api.PurposeOfVisitApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.PurposeDto;
import com.reimbursement.project.dto.PurposeIdDto;
import com.reimbursement.project.dto.PurposesDto;
import com.reimbursement.project.service.PurposeOfVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PurposeOfVisitController implements PurposeOfVisitApi {


    private final PurposeOfVisitService purposeOfVisitService;

    @Override
    public ResponseEntity<ApiResponseDto> getPurposeOfVisit(){

        return purposeOfVisitService.getPurposeOfVisit();
    }

    @Override
    public ResponseEntity<ApiResponseDto> addPurposeOfVisit(PurposesDto purposesDto){
        return purposeOfVisitService.addPurposeOfVisit(purposesDto);
    }
    @Override
    public ResponseEntity<ApiResponseDto> deletePurposeOfVisit(PurposeIdDto purposeIdDto){
        return purposeOfVisitService.deletePurposeOfVisit(purposeIdDto);
    }
    @Override
    public ResponseEntity<ApiResponseDto> editPurposeOfVisit(PurposeDto purposeDto){
        return purposeOfVisitService.editPurposeOfVisit(purposeDto);
    }

}

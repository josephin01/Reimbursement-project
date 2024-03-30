package com.reimbursement.project.service;

import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.PurposeDto;
import com.reimbursement.project.dto.PurposeIdDto;
import com.reimbursement.project.dto.PurposesDto;
import org.springframework.http.ResponseEntity;

public interface PurposeOfVisitService {
    ResponseEntity<ApiResponseDto> getPurposeOfVisit();
    ResponseEntity<ApiResponseDto> addPurposeOfVisit(PurposesDto purposesDto);
    ResponseEntity<ApiResponseDto> deletePurposeOfVisit(PurposeIdDto purposeIdDto);
    ResponseEntity<ApiResponseDto> editPurposeOfVisit(PurposeDto purposeDto);
}

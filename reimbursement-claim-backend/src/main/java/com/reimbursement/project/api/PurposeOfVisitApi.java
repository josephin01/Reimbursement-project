package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.PurposeDto;
import com.reimbursement.project.dto.PurposeIdDto;
import com.reimbursement.project.dto.PurposesDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.ADMIN)
public interface PurposeOfVisitApi {
    @GetMapping(ApiConstant.PURPOSE_OF_VISIT)
    ResponseEntity<ApiResponseDto> getPurposeOfVisit();
    @PostMapping(ApiConstant.PURPOSE_OF_VISIT)
    ResponseEntity<ApiResponseDto> addPurposeOfVisit(@Valid  @RequestBody PurposesDto purposesDto);
    @DeleteMapping(ApiConstant.PURPOSE_OF_VISIT)
    ResponseEntity<ApiResponseDto> deletePurposeOfVisit(@Valid @RequestBody PurposeIdDto purposeIdDto);
    @PutMapping(ApiConstant.PURPOSE_OF_VISIT)
    ResponseEntity<ApiResponseDto> editPurposeOfVisit(@Valid @RequestBody PurposeDto purposeDto);
}

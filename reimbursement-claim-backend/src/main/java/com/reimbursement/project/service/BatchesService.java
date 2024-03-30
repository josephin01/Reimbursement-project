package com.reimbursement.project.service;


import com.reimbursement.project.dto.BatchesRequestDto;
import com.reimbursement.project.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;


public interface BatchesService {
    public ResponseEntity<ApiResponseDto> createBatch(BatchesRequestDto batchesRequestDto);

    public ResponseEntity<ApiResponseDto> getBatches();

    public ResponseEntity<ApiResponseDto> getParticularBatch(Long id);

}

package com.reimbursement.project.controller;


import com.reimbursement.project.api.BatchesApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.BatchesRequestDto;
import com.reimbursement.project.service.BatchesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchesController implements BatchesApi {

    private final BatchesService batchesService;
    @Override
    public ResponseEntity<ApiResponseDto> getExpensesByFilters(BatchesRequestDto batchesRequestDto) {
        return this.batchesService.createBatch(batchesRequestDto);
    }
    @Override
    public ResponseEntity<ApiResponseDto> getBatchHistory() {
        return batchesService.getBatches();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getBatchesById(Long id) {
        return batchesService.getParticularBatch(id);
    }

}

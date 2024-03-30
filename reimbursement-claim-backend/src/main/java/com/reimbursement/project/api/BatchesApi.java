package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.BatchesRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.BATCH)
public interface BatchesApi {
    @PostMapping(ApiConstant.BATCH_FILTERS)
    ResponseEntity<ApiResponseDto> getExpensesByFilters(@Valid @RequestBody BatchesRequestDto batchesRequestDto);
    @GetMapping(ApiConstant.BATCH_HISTORY)
    ResponseEntity<ApiResponseDto> getBatchHistory();
    @GetMapping(ApiConstant.BATCH_ID)
    ResponseEntity<ApiResponseDto> getBatchesById(@PathVariable(name = "id") Long id);

}

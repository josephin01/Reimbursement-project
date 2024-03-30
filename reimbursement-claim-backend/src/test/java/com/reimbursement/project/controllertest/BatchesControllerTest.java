package com.reimbursement.project.controllertest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.controller.BatchesController;
import com.reimbursement.project.dto.BatchesRequestDto;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.service.BatchesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BatchesControllerTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    BatchesService batchesService;
    @InjectMocks
    BatchesController batchesController;
    @Test
    void testGetExpensesByFilters(){
    BatchesRequestDto batchesRequestDto=new BatchesRequestDto(new Date(), new Date(),"demo",null);
    when(batchesController.getExpensesByFilters(batchesRequestDto)).thenThrow(new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND));
        assertThrows(ResourceNotFoundException.class,()->batchesController.getExpensesByFilters(batchesRequestDto));
    }
    @Test
    void testGetBatchHistory(){
        when(batchesController.getBatchHistory()).thenThrow(new ResourceNotFoundException(Constant.NO_BATCH_EXIST));
        assertThrows(ResourceNotFoundException.class,()->batchesController.getBatchHistory());
    }
    @Test
    void testGetBatchesById(){
        Long id=1L;
        when(batchesController.getBatchesById(id)).thenThrow(new ResourceNotFoundException(Constant.NO_BATCH_EXIST));
        assertThrows(ResourceNotFoundException.class,()->batchesController.getBatchesById(id));
    }
}

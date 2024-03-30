package com.reimbursement.project.controllertest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.controller.TravelFormController;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.TravelFormResponseDto;
import com.reimbursement.project.service.TravelFormService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TravelFormControllerTest {

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    TravelFormController travelFormController;

    @Mock
    TravelFormService travelFormService;

    @Test
    void testGetTravelFormData() {


        Long travelFormId = 1L;

        TravelFormResponseDto expectedResponse = TravelFormResponseDto.builder()
                .formId(travelFormId)
                .managerName("Manager")
                .projectName("Project")
                .dateOfTravel(Date.valueOf("2024-04-20"))
                .applyDate(Date.valueOf("2024-04-10"))
                .colleagueDetails("Colleague Details")
                .numberOfPeople(3)
                .projectScope("Project Scope")
                .purpose("Purpose")
                .build();
        ApiResponseDto expectedApiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.DATA_FETCHED)
                .data(expectedResponse)
                .build();
        ApiResponseDto expected = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.DATA_FETCHED)
                .data(TravelFormResponseDto.builder()
                        .formId(travelFormId)
                        .managerName("Manager")
                        .projectName("Project")
                        .dateOfTravel(Date.valueOf("2024-04-20"))
                        .applyDate(Date.valueOf("2024-04-10"))
                        .colleagueDetails("Colleague Details")
                        .numberOfPeople(3)
                        .projectScope("Project Scope")
                        .purpose("Purpose")
                        .build())
                .build();
        ResponseEntity<ApiResponseDto> expectedResult=new ResponseEntity<>(expected,HttpStatus.OK);
        when(travelFormService.getTravelFormData(travelFormId)).thenReturn(expectedResult);


        ResponseEntity<ApiResponseDto> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);
        ResponseEntity<ApiResponseDto> actualResponseEntity = travelFormController.getTravelForm(travelFormId);


        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getHttpStatus(), actualResponseEntity.getBody().getHttpStatus());
        assertEquals(expectedResponseEntity.getBody().getMessage(), actualResponseEntity.getBody().getMessage());
        assertEquals(expectedResponseEntity.getBody().getData(), actualResponseEntity.getBody().getData());
    }

}

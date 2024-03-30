package com.reimbursement.project.controllertest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.controller.PurposeOfVisitController;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.PurposeDto;
import com.reimbursement.project.dto.PurposeIdDto;
import com.reimbursement.project.dto.PurposesDto;
import com.reimbursement.project.service.PurposeOfVisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PurposeOfVisitControllerTest {
    @InjectMocks
    PurposeOfVisitController purposeOfVisitController;

    @Mock
    PurposeOfVisitService purposeOfVisitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPurposeOfVisit(){
        PurposeDto purpose = new PurposeDto();
        purpose.setPurposes("Project Requirement");
        purpose.setId(1L);


        List<PurposeDto> purposeOfVisitList = new ArrayList<>();
        purposeOfVisitList.add(purpose);

        ApiResponseDto apiResponseDto = new ApiResponseDto(HttpStatus.OK, Constant.PURPOSE_FETCHED,purposeOfVisitList);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(apiResponseDto,HttpStatus.OK);

        when(purposeOfVisitService.getPurposeOfVisit()).thenReturn(expectedResult);
        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitController.getPurposeOfVisit();

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testAddPurposeOfVisit(){
        PurposesDto purposesDto=new PurposesDto("Project Requirement");
        String purpose = purposesDto.getPurposes();

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_ADDED,purpose),HttpStatus.OK);
        when(purposeOfVisitService.addPurposeOfVisit(purposesDto)).thenReturn(expectedResult);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitController.addPurposeOfVisit(purposesDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testDeletePurposeOfVisit(){
        PurposeIdDto purposeId = new PurposeIdDto(1L);

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_DELETED,null),HttpStatus.OK);
        when(purposeOfVisitService.deletePurposeOfVisit(purposeId)).thenReturn(expectedResult);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitController.deletePurposeOfVisit(purposeId);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testEditPurposeOfVisit(){
        PurposeDto purposeDto = new PurposeDto(1L,"Project Requirement");

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_EDITED,purposeDto),HttpStatus.OK);
        when(purposeOfVisitService.editPurposeOfVisit(purposeDto)).thenReturn(expectedResult);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitController.editPurposeOfVisit(purposeDto);

        assertEquals(expectedResult,actualResult);

    }
}

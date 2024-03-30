package com.reimbursement.project.servicetest;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.PurposeDto;
import com.reimbursement.project.dto.PurposeIdDto;
import com.reimbursement.project.dto.PurposesDto;
import com.reimbursement.project.entity.PurposeOfVisit;
import com.reimbursement.project.repository.service.PurposeOfVisitRepoService;
import com.reimbursement.project.service.impl.PurposeOfVisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PurposeOfVisitServiceTest {
    @InjectMocks
    PurposeOfVisitServiceImpl purposeOfVisitService;

    @Mock
    PurposeOfVisitRepoService purposeOfVisitRepoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPurposeOfVisit(){

        List<PurposeOfVisit> allPurposes = new ArrayList<>();

        PurposeOfVisit purposeOfVisit1=new PurposeOfVisit(1L,"Project Requirement",null,null,null,null);
        PurposeOfVisit purposeOfVisit2=new PurposeOfVisit(2L,"Project Escalation",null,null,null,null);

        allPurposes.add(purposeOfVisit1);
        allPurposes.add(purposeOfVisit2);

        PurposeDto purpose1 = new PurposeDto(purposeOfVisit1.getId(),purposeOfVisit1.getPurposes());
        PurposeDto purpose2 = new PurposeDto(purposeOfVisit2.getId(),purposeOfVisit2.getPurposes());


        List<PurposeDto> purposeResult = new ArrayList<>();
        purposeResult.add(purpose1);
        purposeResult.add(purpose2);

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK, Constant.PURPOSE_FETCHED,purposeResult),HttpStatus.OK);

        when(purposeOfVisitRepoService.toFindAll()).thenReturn(allPurposes);
        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.getPurposeOfVisit();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void testAddPurposeOfVisit(){

        PurposesDto purposesDto = new PurposesDto("Project requirement");

        when(purposeOfVisitRepoService.isPurposePresent(purposesDto.getPurposes())).thenReturn(Optional.empty());

        PurposeOfVisit purposeOfVisit = new PurposeOfVisit();
        purposeOfVisit.setPurposes(purposesDto.getPurposes());

        when(purposeOfVisitRepoService.toSave(purposeOfVisit)).thenReturn(purposeOfVisit);

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_ADDED,purposesDto.getPurposes()),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.addPurposeOfVisit(purposesDto);

        assertEquals(expectedResult,actualResult);

    }
    @Test
    void testRetrievePurposeOfVisit(){

        PurposesDto purposesDto = new PurposesDto("Project requirement");
        PurposeOfVisit purposeOfVisit = new PurposeOfVisit(1L,purposesDto.getPurposes(),null,null,null, Date.valueOf("2024-03-24"));

        when(purposeOfVisitRepoService.isPurposePresent(purposesDto.getPurposes())).thenReturn(Optional.of(purposeOfVisit));

        when(purposeOfVisitRepoService.toSave(purposeOfVisit)).thenReturn(purposeOfVisit);

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_RETRIEVED,purposesDto.getPurposes()),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.addPurposeOfVisit(purposesDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testAddExistingPurposeOfVisit(){

        PurposesDto purposesDto = new PurposesDto("Project requirement");
        PurposeOfVisit purposeOfVisit = new PurposeOfVisit(1L,purposesDto.getPurposes(),null,null,null,null);

        when(purposeOfVisitRepoService.isPurposePresent(purposesDto.getPurposes())).thenReturn(Optional.of(purposeOfVisit));

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.CONFLICT,Constant.PURPOSE_EXIST,purposesDto.getPurposes()),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.addPurposeOfVisit(purposesDto);

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void testDeletePurposeOfVisit(){

        PurposeIdDto purposeIdDto = new PurposeIdDto(1L);
        PurposeOfVisit purposeOfVisit = new PurposeOfVisit(1L,"Purpose requirement",null,null,null,null);

        when(purposeOfVisitRepoService.toFindById(purposeIdDto.getId())).thenReturn(Optional.of(purposeOfVisit));
        when(purposeOfVisitRepoService.toSave(purposeOfVisit)).thenReturn(purposeOfVisit);
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_DELETED,null),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.deletePurposeOfVisit(purposeIdDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testDeletedPurposeOfVisit(){

        PurposeIdDto purposeIdDto = new PurposeIdDto(1L);
        PurposeOfVisit purposeOfVisit = new PurposeOfVisit(1L,"Purpose requirement",null,null,null,Date.valueOf("2024-03-24"));

        when(purposeOfVisitRepoService.toFindById(purposeIdDto.getId())).thenReturn(Optional.of(purposeOfVisit));
        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.BAD_REQUEST,Constant.PURPOSE_NOT_FOUND,null),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.deletePurposeOfVisit(purposeIdDto);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testEditPurposeOfVisit(){
        PurposeDto purposeDto = new PurposeDto(1L,"Purpose requirement");

        PurposeOfVisit purposeOfVisit=new PurposeOfVisit(1L,"Purpose escalation",null,null,null,null);
         when(purposeOfVisitRepoService.toFindById(purposeDto.getId())).thenReturn(Optional.of(purposeOfVisit));
         when(purposeOfVisitRepoService.isPurposePresent(purposeDto.getPurposes())).thenReturn(Optional.empty());
         purposeOfVisit.setPurposes(purposeDto.getPurposes());
         when(purposeOfVisitRepoService.toSave(purposeOfVisit)).thenReturn(purposeOfVisit);

         ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_EDITED,purposeDto),HttpStatus.OK);

         ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.editPurposeOfVisit(purposeDto);

         assertEquals(expectedResult,actualResult);
    }

    @Test
    void testEditExistingPurposeOfVisit(){
        PurposeDto purposeDto = new PurposeDto(1L,"Purpose requirement");

        PurposeOfVisit purposeOfVisit=new PurposeOfVisit(1L,"Purpose requirement",null,null,null,null);
        when(purposeOfVisitRepoService.toFindById(purposeDto.getId())).thenReturn(Optional.of(purposeOfVisit));
        when(purposeOfVisitRepoService.isPurposePresent(purposeDto.getPurposes())).thenReturn(Optional.of(purposeOfVisit));
        purposeOfVisit.setPurposes(purposeDto.getPurposes());
        when(purposeOfVisitRepoService.toSave(purposeOfVisit)).thenReturn(purposeOfVisit);

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_EXIST,purposeOfVisit.getPurposes()),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.editPurposeOfVisit(purposeDto);

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void testEditNonExistingPurposeOfVisit(){
        PurposeDto purposeDto = new PurposeDto(1L,"Purpose requirement");

        PurposeOfVisit purposeOfVisit=new PurposeOfVisit(2L,"Purpose requirement",null,null,null,null);
        when(purposeOfVisitRepoService.toFindById(purposeDto.getId())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDto> expectedResult = new ResponseEntity<>(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_NOT_EXIST,null),HttpStatus.OK);

        ResponseEntity<ApiResponseDto> actualResult = purposeOfVisitService.editPurposeOfVisit(purposeDto);

        assertEquals(expectedResult,actualResult);
    }



}

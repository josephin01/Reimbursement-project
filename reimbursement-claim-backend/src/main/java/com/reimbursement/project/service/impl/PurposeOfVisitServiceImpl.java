package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.PurposeDto;
import com.reimbursement.project.dto.PurposeIdDto;
import com.reimbursement.project.dto.PurposesDto;
import com.reimbursement.project.entity.PurposeOfVisit;
import com.reimbursement.project.repository.service.PurposeOfVisitRepoService;
import com.reimbursement.project.service.PurposeOfVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurposeOfVisitServiceImpl implements PurposeOfVisitService {

    private final PurposeOfVisitRepoService purposeOfVisitRepoService;

    @Override
    public ResponseEntity<ApiResponseDto> getPurposeOfVisit() {
        List<PurposeOfVisit> purposeOfVisits=purposeOfVisitRepoService.toFindAll();
        List<PurposeDto> purposes=new ArrayList<>();
        for(PurposeOfVisit purpose:purposeOfVisits){
            PurposeDto purposeDto=PurposeDto.builder()
                    .id(purpose.getId())
                    .purposes(purpose.getPurposes())
                    .build();
            purposes.add(purposeDto);
        }

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PURPOSE_FETCHED,purposes));
    }

    @Override
    public ResponseEntity<ApiResponseDto> addPurposeOfVisit(PurposesDto purposesDto) {
        String purpose= StringUtils.capitalize(purposesDto.getPurposes().toLowerCase());
        Optional<PurposeOfVisit> checkPurposeOfVisit=purposeOfVisitRepoService.isPurposePresent(purpose);
        if(checkPurposeOfVisit.isPresent()){
            if(checkPurposeOfVisit.get().getDeletedAt()!=null){
                checkPurposeOfVisit.get().setDeletedAt(null);
                purposeOfVisitRepoService.toSave(checkPurposeOfVisit.get());
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_RETRIEVED,purpose));

            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CONFLICT,Constant.PURPOSE_EXIST,purpose));
        }
        PurposeOfVisit purposeOfVisit=new PurposeOfVisit();
        purposeOfVisit.setPurposes(purpose);
        purposeOfVisitRepoService.toSave(purposeOfVisit);

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_ADDED,purpose));
    }

    @Override
    public ResponseEntity<ApiResponseDto> deletePurposeOfVisit(PurposeIdDto purposeIdDto) {
        Long id=purposeIdDto.getId();
        PurposeOfVisit purposeOfVisit=purposeOfVisitRepoService.toFindById(id).orElseThrow(null);
        if(purposeOfVisit.getDeletedAt()!=null) {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST,Constant.PURPOSE_NOT_FOUND,null));
        }
        purposeOfVisit.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        purposeOfVisitRepoService.toSave(purposeOfVisit);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.PURPOSE_DELETED,null));
    }

    @Override
    public ResponseEntity<ApiResponseDto> editPurposeOfVisit(PurposeDto purposeDto) {
        Long id=purposeDto.getId();
        String purposes=StringUtils.capitalize(purposeDto.getPurposes().toLowerCase());
        Optional<PurposeOfVisit> checkPurposeOfVisit=purposeOfVisitRepoService.toFindById(id);
        if(checkPurposeOfVisit.isPresent()){
            Optional<PurposeOfVisit> checkPurposes=purposeOfVisitRepoService.isPurposePresent(purposes);
            if(checkPurposes.isPresent()){
                    return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_EXIST,purposes));

                }
            PurposeOfVisit purposeOfVisit=checkPurposeOfVisit.get();
            purposeOfVisit.setPurposes(purposes);
            purposeOfVisitRepoService.toSave(purposeOfVisit);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_EDITED,purposeDto));
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.PURPOSE_NOT_EXIST,null));

    }
}

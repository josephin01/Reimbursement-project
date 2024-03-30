package com.reimbursement.project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.*;
import com.reimbursement.project.entity.*;
import com.reimbursement.project.entity.Enum.*;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.*;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.repository.service.NotificationRepoService;
import com.reimbursement.project.repository.service.ProjectsRepoService;
import com.reimbursement.project.repository.service.TravelFormRepoService;

import com.reimbursement.project.service.TravelFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TravelFormServiceImpl implements TravelFormService {

    private final TravelFormRepoService travelFormRepoService;
    private final EmployeeDetailsRepoService employeeDetailsRepoService;
    private final ProjectsRepoService projectsRepoService;
    private final NotificationRepoService notificationRepoService;
    private final PurposeOfVisitRepoService purposeOfVisitRepoService;
    private final ExpensesRepoService expensesRepoService;
    private final RolesRepoService rolesRepoService;

    @Override
    public ResponseEntity<ApiResponseDto>
    addFormDetails(TravelFormsDto travelFormData) throws JsonProcessingException {

        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmpId(travelFormData.getEmployeeId());
        if (employeeDetails.isEmpty()) {
            throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
        }

        Optional<Projects> projectDetails = projectsRepoService.findByProjectId(travelFormData.getProjectId());
        if (projectDetails.isEmpty()) {
            throw new ResourceNotFoundException(Constant.PROJECT_NAME_NOT_FOUND);
        }

        Optional<EmployeeDetails> managerDetails = employeeDetailsRepoService.findByEmpId(travelFormData.getManagerId());
        if (managerDetails.isEmpty()) {
            throw new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND);

        }

        Optional<PurposeOfVisit> purposeOfVisit = purposeOfVisitRepoService.findPurposeById(travelFormData.getPurpose());
        if(purposeOfVisit.isEmpty()){
            throw new ResourceNotFoundException(Constant.PURPOSE_OF_VISIT_NOT_FOUND);
        }


        EmployeeDetails employeeInfo = employeeDetails.get();
        Projects projectsData = projectDetails.get();
        Managers managerInfo = managerDetails.get().getManagers();
        PurposeOfVisit purpose = purposeOfVisit.get();
        ObjectMapper objectMapper = new ObjectMapper();
        String colleagueData = objectMapper.writeValueAsString(travelFormData.getColleague());

        Notification notification = Notification.builder()
                .employeeNotificationStatus(NotifyStatus.SENT)
                .managerNotificationStatus(NotifyStatus.SENT)
                .adminNotificationStatus(NotifyStatus.SENT)
                .notificationFormType(NotificationFormType.TRAVEL_FORM)
                .empId(travelFormData.getEmployeeId())
                .managerId(travelFormData.getManagerId())
                .build();

        notification = notificationRepoService.saveData(notification);

        TravelForm travelForm = TravelForm.builder()
                .employeeDetails(employeeInfo)
                .project(projectsData)
                .travelDate(travelFormData.getDateOfTravel())
                .applyDate(travelFormData.getDate())
                .travelFormStatus(TravelFormStatus.FORM_PENDING)
                .managers(managerInfo)
                .colleagueDetails(colleagueData)
                .purposeOfVisit(purpose)
                .description(travelFormData.getProjectScope())
                .numberOfPeople(travelFormData.getNumberOfPeople())
                .notification(notification)
                .billStatus(BillStatus.NO_BILL)
                .build();

        travelFormRepoService.saveTravelForm(travelForm);
        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(Constant.TRAVEL_FORM_ADDED)
                .data(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormData(Long travelFormId) {
        Optional<TravelForm> travelFormDetails = travelFormRepoService.getTravelForm(travelFormId);
        if (travelFormDetails.isEmpty()) {
            throw new ResourceNotFoundException(Constant.TRAVEL_FORM_NOT_EXISTS);

        }

        TravelForm travelFormData = travelFormDetails.get();
        TravelFormResponseDto travelFormResponse = TravelFormResponseDto.builder()
                .formId(travelFormData.getId())
                .managerName(travelFormData.getManagers().getManagerName())
                .projectName(travelFormData.getProject().getProjectName())
                .employeeName(travelFormData.getEmployeeDetails().getFirstName() + " " + travelFormData.getEmployeeDetails().getLastName())
                .dateOfTravel(travelFormData.getTravelDate())
                .applyDate(travelFormData.getApplyDate())
                .colleagueDetails(travelFormData.getColleagueDetails())
                .numberOfPeople(travelFormData.getNumberOfPeople())
                .projectScope(travelFormData.getDescription())
                .purpose(travelFormData.getPurposeOfVisit().getPurposes())
                .build();

        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.DATA_FETCHED)
                .data(travelFormResponse)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllTravelForms(Long employeeId,String travelFormStatus) {
        try {
            boolean doesEmployeeExists = employeeDetailsRepoService.doesEmpIdExists(employeeId);
            if (!doesEmployeeExists) {
                throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
            }

            List<TravelForm> travelForms;
            if (travelFormStatus.equals(Constant.ALL)) {
                travelForms = travelFormRepoService.getAllTravelFormsByEmployeeId(employeeId);
            } else {
                travelForms = travelFormRepoService.getAllTravelForms(employeeId, TravelFormStatus.valueOf(travelFormStatus));
            }

            List<TravelFormAllDto> employeeAllTravelForms = new ArrayList<>();
            for (TravelForm travelForm : travelForms) {
                boolean travelFormExistsInExpense = expensesRepoService.findTravelFormInExpense(travelForm.getId());
                TravelFormAllDto employeeTravelForms = TravelFormAllDto.builder()
                        .travelId(travelForm.getId())
                        .dateOfTravel(travelForm.getTravelDate())
                        .date(travelForm.getApplyDate())
                        .projectName(travelForm.getProject().getProjectName())
                        .purpose(travelForm.getPurposeOfVisit().getPurposes())
                        .status(travelForm.getTravelFormStatus())
                        .isExpenseExists(travelFormExistsInExpense)
                        .build();
                employeeAllTravelForms.add(employeeTravelForms);
            }

            ApiResponseDto apiResponse = ApiResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(Constant.DATA_FETCHED)
                    .data(employeeAllTravelForms)
                    .build();

            return ResponseEntity.ok(apiResponse);
        }catch (IllegalArgumentException e){
            throw new ResourceNotFoundException(Constant.INVALID_DATA);
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllTravelFormForManager(Long managerId,String status) {
        try {
            Optional<EmployeeDetails> managerDetails = employeeDetailsRepoService.findManager(managerId);
            if (managerDetails.isEmpty()) {
                throw new ResourceNotFoundException(Constant.MANAGER_ID_NOT_FOUND);
            }
            EmployeeDetails managerData = managerDetails.get();
            if (Boolean.FALSE.equals(managerData.getIsManager())) {
                throw new ResourceNotFoundException(Constant.NOT_MANAGER);
            }


            List<TravelFormAllManagersDto> employeeAllTravelForms = new ArrayList<>();
            List<TravelForm> travelForms;

            if (status.equals(Constant.ALL)) {
                travelForms = travelFormRepoService.getAllTravelFormsForManager(managerData.getManagers().getId());
            } else {
                travelForms = travelFormRepoService.getAllTravelFormsForManagerByStatus(managerData.getManagers().getId(), TravelFormStatus.valueOf(status));
            }
            for (TravelForm travelForm : travelForms) {
                Optional<EmployeeDetails> employeeDetails = employeeDetailsRepoService.findByEmpId(travelForm.getEmployeeDetails().getEmpId());
                if (employeeDetails.isEmpty()) {
                    throw new ResourceNotFoundException(Constant.EMPLOYEE_ID_NOT_FOUND);
                }

                EmployeeDetails employeeData = employeeDetails.get();
                TravelFormAllManagersDto employeeTravelForms = TravelFormAllManagersDto.builder()
                        .travelId(travelForm.getId())
                        .employeeName(employeeData.getFirstName() + " " + employeeData.getLastName())
                        .dateOfTravel(travelForm.getTravelDate())
                        .date(travelForm.getApplyDate())
                        .projectName(travelForm.getProject().getProjectName())
                        .purpose(travelForm.getPurposeOfVisit().getPurposes())
                        .status(travelForm.getTravelFormStatus())
                        .build();
                employeeAllTravelForms.add(employeeTravelForms);
            }

            ApiResponseDto apiResponse = ApiResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(Constant.DATA_FETCHED)
                    .data(employeeAllTravelForms)
                    .build();

            return ResponseEntity.ok(apiResponse);
        }catch (IllegalArgumentException e){
            throw new ResourceNotFoundException(Constant.INVALID_DATA);
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllTravelFormsForAdmin(String status) {
        try {
            List<TravelFormAllDto> allTravelForms = new ArrayList<>();
            List<TravelForm> travelForms;
            if (status.equals("ALL")) {
                travelForms = travelFormRepoService.getAllTravelFormsForAdminWithoutStatus();
            } else {
                travelForms = travelFormRepoService.getAllTravelFormsForAdmin(TravelFormStatus.valueOf(status));
            }

            for (TravelForm travelForm : travelForms) {
                TravelFormAllDto employeeTravelForms = TravelFormAllDto.builder()
                        .travelId(travelForm.getId())
                        .dateOfTravel(travelForm.getTravelDate())
                        .date(travelForm.getApplyDate())
                        .projectName(travelForm.getProject().getProjectName())
                        .purpose(travelForm.getPurposeOfVisit().getPurposes())
                        .status(travelForm.getTravelFormStatus())
                        .build();

                allTravelForms.add(employeeTravelForms);
            }

            ApiResponseDto apiResponse = ApiResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(Constant.DATA_FETCHED)
                    .data(allTravelForms)
                    .build();

            return ResponseEntity.ok(apiResponse);
        }catch (IllegalArgumentException e){
            throw new ResourceNotFoundException(Constant.INVALID_DATA);
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto> updateStatus(StatusUpdateDto statusUpdateDto, Long travelFormId) {
        Optional<TravelForm> travelForm = travelFormRepoService.getTravelForm(travelFormId);
        if (travelForm.isEmpty()) {
            throw new ResourceNotFoundException(Constant.TRAVEL_FORM_NOT_EXISTS);
        }

        TravelForm travelFormData = travelForm.get();
        travelFormData.setTravelFormStatus(TravelFormStatus.valueOf(statusUpdateDto.getStatus()));
        travelFormRepoService.saveTravelForm(travelFormData);

        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.STATUS_UPDATED)
                .data(null)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @Override
    public ResponseEntity<ApiResponseDto> getBillsStatusCount() {
        List<Map<String, Object>> billStatusCount = travelFormRepoService.billsStatusCount();
        List<Map<String, Object>> countOfBills = new ArrayList<>();
        String billStatus="bill_status";

        for (Map<String, Object> bill : billStatusCount) {
            Map<String, Object> newBill = new HashMap<>(Map.copyOf(bill));
            short status = (short) bill.get(billStatus);
            switch (status) {
                case 0:
                    newBill.put(billStatus, BillStatus.NO_BILL);
                    break;
                case 1:
                    newBill.put(billStatus, BillStatus.BILL_ADDED);
                    break;
                case 2:
                    newBill.put(billStatus, BillStatus.BILL_APPROVED);
                    break;
                case 3:
                    newBill.put(billStatus, BillStatus.BILL_REJECTED);
                    break;
                default:
                    break;
            }
            countOfBills.add(newBill);
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, Constant.BILLS_COUNT_FETCHED, countOfBills));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormStatusCount() {
        List<Map<String, Object>> travelFromStatusCount = travelFormRepoService.travelFormStatusCount();
        List<Map<String,Object>> countOfTravelForms=new ArrayList<>();
        Set<TravelFormStatus> travelFormStatuses=new HashSet<>();

        String formStatus="travel_form_status";
        for(Map<String,Object> travelForm:travelFromStatusCount){
            Map<String,Object> newTravelForm=new HashMap<>(Map.copyOf(travelForm));
            short status=(short) travelForm.get(formStatus);
            switch (status){
                case 0:
                    newTravelForm.put(formStatus, TravelFormStatus.FORM_PENDING);
                    travelFormStatuses.add(TravelFormStatus.FORM_PENDING);
                    break;
                case 1:
                    newTravelForm.put(formStatus, TravelFormStatus.FORM_APPROVED);
                    travelFormStatuses.add(TravelFormStatus.FORM_APPROVED);
                    break;
                case 2:
                    newTravelForm.put(formStatus, TravelFormStatus.FORM_REJECTED);
                    travelFormStatuses.add(TravelFormStatus.FORM_REJECTED);
                    break;
                default:
                    break;
            }
            countOfTravelForms.add(newTravelForm);
        }

        for(TravelFormStatus travelFormStatus:TravelFormStatus.values()){
            if(!travelFormStatuses.contains(travelFormStatus)){
                Map<String,Object> addNew=new HashMap<>();
                addNew.put("travel_form_status",travelFormStatus);
                addNew.put("travel_form_status_count",0);
                countOfTravelForms.add(addNew);
            }
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK,Constant.TRAVEL_FORM_STATUS_COUNT,countOfTravelForms));
    }


    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormByStatus(String status) {
        TravelFormStatus enumStatus = TravelFormStatus.valueOf(status.toUpperCase());
        List<TravelForm> travelForm = travelFormRepoService.findAllByStatus(enumStatus);

        if(!travelForm.isEmpty()){
            List<TravelFormListDto> travelFormDtoList = travelForm.stream()
                    .map(form -> new TravelFormListDto(
                            form.getId(),
                            form.getEmployeeDetails().getProfile(),
                            form.getEmployeeDetails().getFirstName(),
                            form.getApplyDate(),
                            form.getPurposeOfVisit().getPurposes(),
                            form.getTravelFormStatus(),
                            form.getTravelDate()
                    )).toList();

            return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK,Constant.TRAVEL_FORM_RETRIEVED , travelFormDtoList));
        }
        else {
            throw new ResourceNotFoundException(Constant.TRAVEL_FORM_NOT_FOUND_WITH_STATUS +status.toUpperCase());
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getTravelFormByRole(String role) {
        Optional<Roles> roles = rolesRepoService.findByRoleName(role);
        if(roles.isEmpty()){
            throw new ResourceNotFoundException(Constant.INVALID_ROLE);
        }
        List<TravelForm> allTravelForms = new ArrayList<>();
        Roles roleData = roles.get();
        if(roleData.getId() == 2){
            allTravelForms = travelFormRepoService.findAllTravelFormForAdminByRoles(2L);
        }else if(roleData.getId() == 3) {
            allTravelForms = travelFormRepoService.findAllTravelFormForAdminByRoles(3L);
        }
            List<TravelFormListDto> travelFormListDtos = new ArrayList<>();

            if(!allTravelForms.isEmpty()) {
               travelFormListDtos = allTravelForms.stream()
                       .filter(form -> form.getTravelFormStatus().equals(TravelFormStatus.FORM_PENDING))
                        .map(form -> new TravelFormListDto(
                                form.getId(),
                                form.getEmployeeDetails().getProfile(),
                                form.getEmployeeDetails().getFirstName(),
                                form.getApplyDate(),
                                form.getPurposeOfVisit().getPurposes(),
                                form.getTravelFormStatus(),
                                form.getTravelDate()
                        )).toList();
            }
        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message(Constant.DATA_FETCHED)
                .data(travelFormListDtos)
                .build();

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}


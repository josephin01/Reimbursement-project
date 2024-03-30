package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ChangeNotificationStatusDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.NOTIFICATION)

public interface NotificationApi {

    @GetMapping(ApiConstant.NOTIFICATION_EMPLOYEE)
    ResponseEntity<ApiResponseDto> employeeTravelFormNotification(@PathVariable Long empId);

    @PutMapping(ApiConstant.NOTIFICATION_UPDATE_EMPLOYEE)
    ResponseEntity<ApiResponseDto> updateFormNotificationStatus(@Valid @RequestBody ChangeNotificationStatusDto employeeIds);

    @GetMapping(ApiConstant.NOTIFICATION_MANAGER)
    ResponseEntity<ApiResponseDto> managerFormNotification(@PathVariable Long managerId);

    @GetMapping(ApiConstant.NOTIFICATION_ADMIN)
    ResponseEntity<ApiResponseDto> adminNotification();

}

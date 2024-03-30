package com.reimbursement.project.controller;

import com.reimbursement.project.api.NotificationApi;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ChangeNotificationStatusDto;
import com.reimbursement.project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<ApiResponseDto> employeeTravelFormNotification(Long empId) {
        return notificationService.getEmployeeTravelFormNotification(empId);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateFormNotificationStatus(ChangeNotificationStatusDto employeeIds) {
        return notificationService.updateNotificationStatus(employeeIds);
    }

    @Override
    public ResponseEntity<ApiResponseDto> managerFormNotification(Long managerId) {
        return notificationService.getManagerNotification(managerId);
    }

    @Override
    public ResponseEntity<ApiResponseDto> adminNotification() {
        return notificationService.getAdminNotification();
    }
}

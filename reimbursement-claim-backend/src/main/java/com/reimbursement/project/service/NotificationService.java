package com.reimbursement.project.service;

import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.ChangeNotificationStatusDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface NotificationService {


    ResponseEntity<ApiResponseDto> getEmployeeTravelFormNotification(Long empId);

    ResponseEntity<ApiResponseDto> updateNotificationStatus(ChangeNotificationStatusDto employeeIds);

    ResponseEntity<ApiResponseDto> getManagerNotification(Long managerId);

    ResponseEntity<ApiResponseDto> getAdminNotification();

    void notifyCertification() throws MessagingException;
}

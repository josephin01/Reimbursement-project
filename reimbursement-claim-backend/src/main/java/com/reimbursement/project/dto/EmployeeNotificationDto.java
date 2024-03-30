package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Enum.NotificationFormType;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNotificationDto {

    private Long notificationId;

    private NotificationFormType formType;

    private String status;

//    private String updatedBy;
}

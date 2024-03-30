package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Enum.NotificationFormType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerNotificationResponseDto {

    private Long empId;

    private Long notificationId;

    private NotificationFormType formType;

    private String name;

}

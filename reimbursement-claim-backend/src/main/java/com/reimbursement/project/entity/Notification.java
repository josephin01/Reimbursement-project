package com.reimbursement.project.entity;

import com.reimbursement.project.entity.Enum.NotificationFormType;
import com.reimbursement.project.entity.Enum.NotifyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private NotifyStatus employeeNotificationStatus;
    private NotifyStatus employeeAdminNotificationStatus;

    private NotifyStatus managerNotificationStatus;

    private NotifyStatus adminNotificationStatus;

    private Date date;

    private NotificationFormType notificationFormType;

    private Long empId;

    private Long managerId;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private Date deletedAt;

}


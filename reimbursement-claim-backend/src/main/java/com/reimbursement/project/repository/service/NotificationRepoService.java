package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Notification;

import java.util.Optional;

public interface NotificationRepoService {

    Notification toSave(Notification notification);

    Optional<Notification> toFindById(Long id);

    Notification saveData(Notification notification);

    Optional<Notification> findById(Long notificationId);
    void save(Notification notification);

}

package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Notification;
import com.reimbursement.project.repository.NotificationRepository;
import com.reimbursement.project.repository.service.NotificationRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationRepoServiceImpl implements NotificationRepoService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification saveData(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(Long notificationId) {
        return notificationRepository.findById(notificationId);

    }
    @Override
    public Notification toSave(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> toFindById(Long id) {
        return notificationRepository.findById(id);
    }


    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

}

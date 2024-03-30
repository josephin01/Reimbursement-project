package com.reimbursement.project.repository;

import com.reimbursement.project.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Notification save(Notification notification);

    Optional<Notification> findById(Long id);

}

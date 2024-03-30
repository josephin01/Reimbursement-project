package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Session;

import java.util.Optional;

public interface SessionRepoService {
    Session saveSession(Session session);

    Optional<Session> findBySessionId(Long sid);
}

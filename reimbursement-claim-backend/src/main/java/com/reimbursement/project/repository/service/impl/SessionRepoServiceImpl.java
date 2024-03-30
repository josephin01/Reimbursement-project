package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Session;
import com.reimbursement.project.repository.SessionRepository;
import com.reimbursement.project.repository.service.SessionRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionRepoServiceImpl implements SessionRepoService {

    private final SessionRepository sessionRepository;
    @Override
    public Session saveSession(Session session) {
       return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> findBySessionId(Long sid) {
        return sessionRepository.findById(sid);
    }
}

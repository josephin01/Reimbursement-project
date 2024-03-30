package com.reimbursement.project.service;

import java.util.Map;

public interface OAuthUserInfoService {
    Map<String, Object> getUserInfo(String accessToken);
}

package com.reimbursement.project.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.service.OAuthUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuthUserInfoServiceImpl implements OAuthUserInfoService {

    private final RestTemplate restTemplate;

    @Value("${oauth.userinfo-endpoint}")
    private String urlEndPoint;
    @Override
    public Map<String, Object> getUserInfo(String accessToken) {
        String url = urlEndPoint + accessToken;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(Constant.AUTHORIZATION, Constant.BEARER + accessToken.trim());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}

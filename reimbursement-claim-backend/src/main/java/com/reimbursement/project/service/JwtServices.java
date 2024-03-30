package com.reimbursement.project.service;

import com.reimbursement.project.dto.JwtExtractorDto;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.SignatureException;
import java.util.Map;
import java.util.function.Function;


@Service
public interface JwtServices {
    JwtExtractorDto extractUserName(String token) throws SignatureException;

    String getJwtToken(String userDetails,Long id);

    String generateToken(Map<String, Object> claims, String userDetails);

    String getRefreshToken(String userDetails,Long id);

    <T> T extractClaims(String token, Function<Claims,T> claimsResolver);

    Claims extractAllDetails(String token);

    boolean isTokenValid(String token, UserDetails userDetails) throws SignatureException;
}

package com.reimbursement.project.service.impl;


import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.JwtExtractorDto;
import com.reimbursement.project.service.JwtServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServicesImplementation implements JwtServices {

    @Override
    public JwtExtractorDto extractUserName(String token) throws SignatureException {
        Claims claims = extractAllDetails(token);
        return JwtExtractorDto.builder()
                .sid( ((Number) claims.get(Constant.SESSION_ID)).longValue())
                .username(claims.getSubject())
                .build();
    }

    @Override
    public String getJwtToken(String userDetails,Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.SESSION_ID, id);
        return generateToken(claims, userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> claims, String userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *  20 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getRefreshToken(String userDetails,Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.SESSION_ID, id);
        claims.put(Constant.EMAIL,userDetails);
        return Jwts.builder()
                .setSubject(userDetails)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllDetails(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllDetails(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) throws SignatureException {
        final String username = extractUserName(token).getUsername();
        return username.equals(userDetails.getUsername()) && !isNotExpired(token);
    }

    private boolean isNotExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Constant.KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

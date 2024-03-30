package com.reimbursement.project.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.dto.ApiResponseDto;
import com.reimbursement.project.dto.JwtExtractorDto;
import com.reimbursement.project.entity.Session;
import com.reimbursement.project.exception.InvalidException;
import com.reimbursement.project.exception.ResourceNotFoundException;
import com.reimbursement.project.repository.service.SessionRepoService;
import com.reimbursement.project.repository.service.impl.EmployeeDetailsRepoServiceImpl;
import com.reimbursement.project.service.JwtServices;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final EmployeeDetailsRepoServiceImpl userService;
    private final SessionRepoService sessionRepoService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Constant.AUTHORIZATION);
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith(Constant.BEARER)) {
            token = authHeader.substring(7);
            try {
                JwtExtractorDto jwtData = jwtServices.extractUserName(token);
                username = jwtData.getUsername();
                Long sessionId = jwtData.getSid();
                Session sessionData = sessionRepoService.findBySessionId(sessionId)
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.INVALID_SESSION));
                if(sessionData.getLogoutTime() != null){
                    throw new InvalidException(Constant.SESSION_EXPIRED);
                }
            }catch (ExpiredJwtException e){
                jwtExceptionHandler(response,Constant.JWT_EXPIRED);
                return;
            }catch (Exception e) {
                jwtExceptionHandler(response, e.getMessage());
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            try {
                if (jwtServices.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                jwtExceptionHandler(response, e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void jwtExceptionHandler(@NonNull HttpServletResponse response, String e) throws IOException {
        ApiResponseDto apiResponse = ApiResponseDto.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(e)
                .build();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        response.getWriter().flush();
    }
}

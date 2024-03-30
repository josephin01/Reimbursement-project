package com.reimbursement.project.api;

import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(ApiConstant.AUTH_APIS)
public interface LoginApi {

    @PostMapping
    ResponseEntity<ApiResponseDto> getUserIfExists(@Valid @RequestBody LoginRequestDto token);

    @PostMapping(ApiConstant.AUTH_ADD_DETAILS)
    ResponseEntity<ApiResponseDto> addUserDetails(@Valid @RequestBody UserDataDto userData);

    @PostMapping(ApiConstant.AUTH_REGENERATE_TOKEN)
    ResponseEntity<ApiResponseDto> regenerateToken(@Valid @RequestBody RegenerateTokenRequestDto regenerateTokenRequestDto);

    @PostMapping(ApiConstant.AUTH_LOGOUT)
    ResponseEntity<ApiResponseDto> logoutUser(@Valid @RequestBody LogoutRequestDto logoutData);
}

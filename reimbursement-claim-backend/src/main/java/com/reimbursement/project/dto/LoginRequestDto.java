package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotNull(message = "Access Token Missing")
    private String accessToken;
}

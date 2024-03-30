package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegenerateTokenResponseDto {

    private String accessToken;

    private String refreshToken;
}

package com.reimbursement.project.dto;

import com.reimbursement.project.entity.EmployeeDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private EmployeeDetailsLoginResponseDto employeeDetails;
}

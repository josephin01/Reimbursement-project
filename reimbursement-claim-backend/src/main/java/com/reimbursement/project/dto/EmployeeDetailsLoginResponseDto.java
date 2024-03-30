package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetailsLoginResponseDto {
    private Long id;
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String profile;
    private Long role;
}

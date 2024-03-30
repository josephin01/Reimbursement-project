package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeesDto {
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dob;
    private Boolean isManager;
    private String roleName;
}

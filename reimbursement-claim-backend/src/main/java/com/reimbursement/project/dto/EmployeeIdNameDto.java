package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeIdNameDto {
    private Long empId;
    private String firstName;
    private String lastName;
}

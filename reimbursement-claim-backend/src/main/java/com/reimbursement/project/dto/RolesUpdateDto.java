package com.reimbursement.project.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesUpdateDto {

    @NotNull(message = "Employee Id should not be empty")
    @Digits(integer = 16,fraction = 0,message = "Employee id should be of type Long")
    private Long empId;
    @NotNull(message = "Role name should not be empty")
    @Size(max=25, message = "Role name should not be greater than 25 characters")
    private String roleName;
    private Boolean isTeam;
}

package com.reimbursement.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {

    @NotNull(message = "First Name Missing")
    private String firstName;
    @NotNull(message = "Last Name Missing")
    private String lastName;
    @NotNull(message = "Contact Number Missing")
    @Size(min=10,max = 10,message = "Contact Number Length should be 10")
    private String contact;
    @Email(message = "Not a Valid Email")
    @NotNull(message = "Email is Missing")
    private String email;
    private String profile;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @NotNull(message = "Employee Id missing")
    private Long empId;
}

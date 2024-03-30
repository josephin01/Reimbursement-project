package com.reimbursement.project.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private Long empId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String role;
    private String phone;
    private String email;


}

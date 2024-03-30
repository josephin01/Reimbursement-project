package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDetailsListDto {

    private Long id;

    private String employeeName;

    private Long empId;

    private String phone;

    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

}

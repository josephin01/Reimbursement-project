package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTableAdminResponseDto {
    private String firstName;
    private String profile;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expenseDate;
    private String expenseStatus;
    private Float expenseAmount;

    private String projectName;
    private Long id;
}

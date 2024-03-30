package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTableResponseDto {
    private Date expenseDate;
    private String expenseStatus;
    private Float expenseAmount;

    private String projectName;
    private Long id;

}

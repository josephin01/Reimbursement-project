package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseClaimsListDto {

    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date applyDate;

    private String expenseType;

    private String expenseStatus;

    private Float expenseAmount;

}

package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseClaimResponseDto {

    private String expenseType;

    private Long employeeId;

    private String managerName;

    private String projectName;

    private String colleagueDetails;

    private String expenseDescription;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expenseDate;

    private List<BillsListDto> bills;

    private Float expenseAmount;

}

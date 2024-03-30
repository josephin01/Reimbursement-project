package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseClaimsListDataDto {

    private Long id;

    private String profile;

    private String employeeName;

    private Date applyDate;

    private String projectName;

    private String expenseStatus;

    private Float expenseAmount;

}

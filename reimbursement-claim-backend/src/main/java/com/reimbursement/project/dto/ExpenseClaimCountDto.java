package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseClaimCountDto {

    private String title;
    private Integer count;

}

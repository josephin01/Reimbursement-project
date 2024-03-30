package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Enum.ExpenseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDto{
    private Long id;

    private String expenseDescription;

    private Date expenseDate;

    private String expenseType;

    private List<BillsDto> bills;

    private Float expenseAmount;

    private ExpenseStatus expenseStatus;
}

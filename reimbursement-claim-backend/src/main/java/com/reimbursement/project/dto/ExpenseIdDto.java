package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseIdDto {

    @NotNull(message = "Expense Status should not be empty")
    @Size(max = 25,message = "Expense status should not be greater than 25 characters")
    private String expenseStatus;

    private String remarks;
}

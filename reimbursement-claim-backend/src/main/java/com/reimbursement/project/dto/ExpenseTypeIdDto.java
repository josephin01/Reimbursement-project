package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTypeIdDto {
    @NotNull(message = "Expense Type Id should not be null")
    private Long id;
}

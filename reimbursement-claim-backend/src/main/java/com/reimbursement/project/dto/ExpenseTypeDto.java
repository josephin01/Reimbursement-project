package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Enum.FormType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseTypeDto {
    private Long id;
    @NotBlank(message = "Expense Type should not be empty")
    private String expenses;
    @NotNull(message = "Form type should not be null")
    private FormType formType;

}

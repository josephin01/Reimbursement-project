package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Enum.FormType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormTypeDto {
    @NotNull(message = "Form type should not be null")
    private FormType formType;
}

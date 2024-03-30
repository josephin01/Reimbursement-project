package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurposesDto {
    @NotBlank(message = "purposes should not be empty")
    private String purposes;
}

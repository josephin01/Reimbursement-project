package com.reimbursement.project.dto;

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
public class PurposeDto {
    @NotNull(message = "Purpose Id should not be null")
    private Long id;
    @NotBlank(message = "Purpose name should not be empty")
    private String purposes;
}

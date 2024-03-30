package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillsDto {
    private Long id;
    @NotNull(message = "Bill Url should not be empty")
    @Size(max=150, message = "Bill Url should not be greater than 150 characters")
    private String billsUrl;

    @NotNull(message = "Bill type should not be empty")
    @Size(max=50, message = "Bill type should not be greater than 50 characters")
    private String billType;

    private String billName;
}


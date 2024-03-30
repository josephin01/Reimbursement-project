package com.reimbursement.project.dto;

import com.reimbursement.project.entity.EmployeeDetails;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchesRequestDto {
    @NotNull(message = "From date should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @NotNull(message = "To date should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    @NotNull(message = "The references should not be empty")
    @Size(max = 30, message = "The reference should be less than 30")
    private String references;

    private EmployeeDetails employeeDetails;
}

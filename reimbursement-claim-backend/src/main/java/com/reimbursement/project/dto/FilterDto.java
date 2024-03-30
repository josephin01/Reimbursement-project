package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Enum.ExpenseStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FilterDto {

    private List<Long> empId;
    private List<String> expenses;
    private List<String> projectName;
    private ExpenseStatus expenseStatus;
    private Float maxAmount;
    private Float minAmount;
    private String startDate;
    private String endDate;
    @NotEmpty(message = "Report type should not be empty")
    private String reportType;

}

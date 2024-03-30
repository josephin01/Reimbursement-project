package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchInfoResponseDto {
    private List<ExpensesDto> expensesDto;
}

package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchesFilterResponseDto {
    private Long id;
    private String colleaguesDto;
    private String managerName;
    private String projectName;
    private int numberOfPeople;
    private String purposeOfVisit;
    private String description;
    private Date applyDate;
    private Date travelDate;
    private List<ExpenseResponseDto> expenseResponseDto;

}

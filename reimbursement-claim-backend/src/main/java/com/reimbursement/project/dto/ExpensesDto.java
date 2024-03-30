package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reimbursement.project.entity.TravelForm;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesDto {

    private TravelForm travelFormId;
    @NotNull(message = "Travel form should not be empty")
    @Digits(integer = 16,fraction = 0,message = "Travel form id should be of type Long")
    private Long id;
    private String purposeOfVisit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;
    private String colleagueDetails;
    private Integer numberOfPeople;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date travelDate;
    private String projectScope;
    private String projectName;
    private String managerName;
    @NotNull(message = "Expenses should not be empty")
    private List<ExpensesListDto> expensesList;
}

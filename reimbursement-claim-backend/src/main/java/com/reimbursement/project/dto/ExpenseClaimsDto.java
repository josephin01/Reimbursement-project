package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseClaimsDto {

    private Long id;

    @NotNull(message = "Expense type is Mandatory")
    private Long expenseType;

    @NotNull(message = "Employee ID is Mandatory")
    private Long employeeId;

    @NotNull(message = "Manager Name is Mandatory")
    private Long managers;

    private Long projects;

    private List<ColleagueDto> colleague;

    @Size(min = 0, max = 200, message= "Expense Description should have a maximum length of 100 characters.")
    private String expenseDescription;

    @NotNull(message = "Apply Date is Mandatory")
    private Date applyDate;

    @NotNull(message = "Expense Date is Mandatory")
    private Date expenseDate;

    @NotNull(message = "Bills is mandatory")
    private List<BillsListDto> billsDto;

    @NotNull(message = "Expense Amount is Mandatory")
    private Float expenseAmount;

}

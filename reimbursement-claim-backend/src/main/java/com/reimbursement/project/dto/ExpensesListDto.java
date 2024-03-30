package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesListDto {

        private Long id;
        @NotNull(message = "Expense type should not be empty")
        @Digits(integer = 16, fraction = 0, message = "Expense type should be of type Long")
        private ExpenseTypeDto expenseType;

        @NotNull(message = "Expense description should not be empty")
        @Size(max=255, message = "Expense description should be less than 255 characters")
        private String expenseDescription;

        @NotNull(message = "Expense date should not be empty")
//        @DateTimeFormat(pattern = "yyyy-MM-dd")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date expenseDate;

        @NotNull(message = "Expense amount should not be empty")
        @DecimalMax(value = "1000000.0", inclusive = false, message = "Expense amount should be less than 1000000.0")
        private Float expenseAmount;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date applyDate;

        private List<BillsDto> bills;
    }


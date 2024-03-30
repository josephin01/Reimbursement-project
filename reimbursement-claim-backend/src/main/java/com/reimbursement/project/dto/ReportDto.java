package com.reimbursement.project.dto;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReportDto {
    private Integer sNO;
    private Long employeeID;
    private String projectName;
    private String managerName;
    private LocalDate appliedDate;
    private Float amount;
    private String expenseType;
    private String status;



}

package com.reimbursement.project.dto;

import com.reimbursement.project.entity.Projects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsDto {
    private Long employeeId;
    private Projects project;
    private String managerName;
    private Date applyDate;





}

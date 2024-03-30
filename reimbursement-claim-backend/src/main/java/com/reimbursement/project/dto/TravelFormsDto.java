package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelFormsDto {
    @NotNull(message = "EmployeeId should not be null")
    private Long employeeId;
    @NotNull(message = "ManagerId should not be null")
    private Long managerId;
    @NotNull(message = "ProjectId should not be null")
    private Long projectId;
    @NotNull(message = "Purpose should not be null")
    private Long purpose;
    @NotNull(message = "Date should not be null")
    private Date date;

    private List<ColleagueDto> colleague;
    @NotNull(message = "Number of People should not be null")
    private Integer numberOfPeople;

    private Date dateOfTravel;
    @NotNull(message = "Project Scope should not be null")
    private String projectScope;

}

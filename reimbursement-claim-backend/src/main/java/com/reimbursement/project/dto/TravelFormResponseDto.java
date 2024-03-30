package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelFormResponseDto {

    private Long formId;

    private String managerName;

    private String projectName;

    private String employeeName;

    private String purpose;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfTravel;

    private String colleagueDetails;

    private Integer numberOfPeople;

    private String projectScope;
}

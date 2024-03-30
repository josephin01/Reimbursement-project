package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelFormListDto {

    private Long travelId;

    @JsonProperty("Profile")
    private String profile;

    @JsonProperty("Employee Name")
    private String employeeName;

    @JsonProperty("Apply Date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    @JsonProperty("Purpose of Visit")
    private String purposeOfVisit;

    @JsonProperty("Status")
    private TravelFormStatus travelFormStatus;

    @JsonProperty("Travel Date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date travelDate;

}

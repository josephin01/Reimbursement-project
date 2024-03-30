package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelFormAllManagersDto {

    private Long travelId;

    private String employeeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String projectName;

    private TravelFormStatus status;

    private String purpose;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfTravel;
}

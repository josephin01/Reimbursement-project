package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelFormDto {
    private Long id;
    private Integer numberOfPeople;
    private String description;
    private List<ColleaguesDto> colleagueDetails;

}

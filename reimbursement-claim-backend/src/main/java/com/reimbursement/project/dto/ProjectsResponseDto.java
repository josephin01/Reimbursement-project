package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectsResponseDto {

    private Long id;

    private String projectName;

    private String managers;

    private Long managerId;

}

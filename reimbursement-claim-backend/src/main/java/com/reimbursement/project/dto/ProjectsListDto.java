package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectsListDto {

    private Long projectId;
    private String projectName;
    private Long managerId;
    private String managers;

}

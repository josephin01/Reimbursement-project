package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectsDto {

    @NotNull(message = "Project Name is mandatory")
    private String projectName;

    @NotNull(message = "Manager Id is mandatory")
    private Long managers;


}

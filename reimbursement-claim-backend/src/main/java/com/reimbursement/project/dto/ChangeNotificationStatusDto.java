package com.reimbursement.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeNotificationStatusDto {

    @NotNull(message = "Role should not be null")
    private String role;

    private List<UpdateNotificationStatusDto> updateNotificationStatus;
}

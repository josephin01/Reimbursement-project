package com.reimbursement.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchesDto {
    private Long id;
    private Long empId;
    private String empName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
    private String reference;
}

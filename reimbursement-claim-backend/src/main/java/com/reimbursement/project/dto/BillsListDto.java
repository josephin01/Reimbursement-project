package com.reimbursement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillsListDto {

    private Long id;
    private String billName;
    private String billsUrl;
    private String billType;

}
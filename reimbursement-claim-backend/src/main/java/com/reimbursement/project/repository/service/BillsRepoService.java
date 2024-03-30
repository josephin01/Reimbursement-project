package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Bills;

import java.util.List;

public interface BillsRepoService {

    List<Bills> toSaveAll(List<Bills> bills);

    List<Bills> toFindAllByExpensesId(Long id);
}

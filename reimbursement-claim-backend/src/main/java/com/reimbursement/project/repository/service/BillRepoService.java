package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.Bills;

import java.util.List;

public interface BillRepoService {

    List<Bills> findAllBills(Long expenseClaimsId);

    List<Bills> findValidBills(Long id);

    void saveAll(List<Bills> bills);

    void deleteAllBills(List<Bills> bills1);

}

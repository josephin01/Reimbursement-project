package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Bills;
import com.reimbursement.project.repository.BillsRepository;
import com.reimbursement.project.repository.service.BillRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillRepoServiceImpl implements BillRepoService {

    private final BillsRepository billsRepository;

    @Override
    public List<Bills> findAllBills(Long expenseClaimsId) {
        return billsRepository.findAllByExpenseClaims_Id(expenseClaimsId);
    }

    @Override
    public List<Bills> findValidBills(Long id) {
        return billsRepository.findAllByExpenseClaims_IdAndDeletedAtIsNull(id);
    }

    public void saveAll(List<Bills> bills) {
        billsRepository.saveAll(bills);
    }


    @Override
    public void deleteAllBills(List<Bills> bills1) {
        billsRepository.deleteAll(bills1);
    }

}

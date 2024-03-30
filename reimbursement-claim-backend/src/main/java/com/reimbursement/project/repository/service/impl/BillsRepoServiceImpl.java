package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.Bills;
import com.reimbursement.project.repository.BillsRepository;
import com.reimbursement.project.repository.service.BillsRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillsRepoServiceImpl implements BillsRepoService {

    private final BillsRepository billsRepository;

    @Override
    public List<Bills> toSaveAll(List<Bills> bills) {
        return billsRepository.saveAll(bills);
    }

    @Override
    public List<Bills> toFindAllByExpensesId(Long id) {
        return billsRepository.findAllByExpensesId(id);
    }

}

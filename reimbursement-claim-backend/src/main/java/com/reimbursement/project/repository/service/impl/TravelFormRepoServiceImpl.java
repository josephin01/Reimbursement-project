package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import com.reimbursement.project.entity.TravelForm;
import com.reimbursement.project.repository.TravelFormRepository;
import com.reimbursement.project.repository.service.TravelFormRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelFormRepoServiceImpl implements TravelFormRepoService {

    private final TravelFormRepository travelFormRepository;
    @Override
    public TravelForm saveTravelForm(TravelForm travelForm) {
        return travelFormRepository.save(travelForm);
    }

    @Override
    public Optional<TravelForm> getTravelForm(Long travelFormId) {
        return travelFormRepository.findById(travelFormId);
    }

    @Override
    public List<TravelForm> getAllTravelForms(Long employeeId) {
        return travelFormRepository.findAllByEmployeeDetails_EmpIdOrderByCreatedAtDesc(employeeId);
    }

    @Override
    public List<TravelForm> getAllTravelForms(Long employeeId,TravelFormStatus travelFormStatus) {
        return travelFormRepository.findAllByEmployeeDetails_EmpIdAndTravelFormStatusOrderByCreatedAtDesc(employeeId,travelFormStatus);
    }

    @Override
    public List<TravelForm> getAllTravelFormsForManager(Long managerId) {
        return travelFormRepository.findAllByManagers_IdOrderByCreatedAtDesc(managerId);
    }

    @Override
    public List<TravelForm> getAllTravelFormsForAdmin(TravelFormStatus status) {
        return travelFormRepository.findAllByTravelFormStatusOrderByCreatedAtDesc(status);
    }

    @Override
    public List<TravelForm> findByEmpId(Long id) {
        return travelFormRepository.findAllByEmployeeDetails_IdOrderByCreatedAtDesc(id);
    }

    @Override
    public List<TravelForm> findByManagerId(Long managerId) {
        return travelFormRepository.findAllByManagers_IdAndTravelFormStatusOrderByCreatedAtDesc(managerId, TravelFormStatus.FORM_PENDING);
    }

    @Override
    public List<TravelForm> findAllTravelForm() {
        return travelFormRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<TravelForm> toFindAll() {
        return travelFormRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<TravelForm> getAllTravelFormsByEmployeeId(Long employeeId) {
        return travelFormRepository.findAllByEmployeeDetails_EmpIdOrderByCreatedAtDesc(employeeId);
    }

    @Override
    public List<TravelForm> getAllTravelFormsForManagerByStatus(Long id, TravelFormStatus status) {
        return travelFormRepository.findAllByManagers_IdAndTravelFormStatusOrderByCreatedAtDesc(id,status);
    }

    @Override
    public List<TravelForm> getAllTravelFormsForAdminWithoutStatus() {
        return travelFormRepository.findAllByOrderByCreatedAtDesc();
    }
    @Override
    public List<TravelForm> findAllTravelFormsByBatchId(Long batchId) {
        return travelFormRepository.findAllByBatch_IdOrderByCreatedAtDesc(batchId);
    }

    @Override
    public List<TravelForm> findAllByEmployeeDetails(EmployeeDetails employeeDetailsId) {
        return travelFormRepository.findAllByEmployeeDetailsOrderByCreatedAtDesc(employeeDetailsId);
    }

    @Override
    public List<TravelForm> filters(Date fromDate, Date toDate, String firstName, Long empId) {
        return travelFormRepository.findByEmployeeDetails_EmpIdAndApplyDateBetweenAndEmployeeDetails_FirstName(empId,fromDate,toDate,firstName);

    }

    @Override
    public List<Map<String, Object>> travelFormStatusCount() {
        return travelFormRepository.getTravelFormStatusCounts();
    }

    @Override
    public List<Map<String, Object>> billsStatusCount() {
        return travelFormRepository.getBillsStatusCount();
    }


    @Override
    public Optional<TravelForm> toFindById(Long id) {
        return travelFormRepository.findById(id);
    }


    @Override
    public TravelForm toSave(TravelForm travelForm) {
        return travelFormRepository.save(travelForm);
    }

    @Override
    public List<TravelForm> findAllByStatus(TravelFormStatus status) {
        return travelFormRepository.findAllByTravelFormStatusOrderByCreatedAtDesc(status);

    }

    @Override
    public List<TravelForm> findAllTravelFormForAdminByRoles(Long id) {
        return travelFormRepository.findAllByEmployeeDetails_Role_IdOrderByCreatedAt(id);
    }

}

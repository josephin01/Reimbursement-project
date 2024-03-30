package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import com.reimbursement.project.entity.TravelForm;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TravelFormRepoService {
    List<TravelForm> filters(Date fromDate, Date toDate, String firstName, Long empId);
    List<Map<String, Object>> travelFormStatusCount();
    List<Map<String, Object>> billsStatusCount();
    Optional<TravelForm> toFindById(Long id);
    TravelForm toSave(TravelForm travelForm);
    TravelForm saveTravelForm(TravelForm travelForm);
    Optional<TravelForm> getTravelForm(Long travelFormId);
    List<TravelForm> getAllTravelForms(Long employeeId);
    List<TravelForm> getAllTravelForms(Long employeeId, TravelFormStatus travelFormStatus);
    List<TravelForm> getAllTravelFormsForManager(Long managerId);
    List<TravelForm> getAllTravelFormsForAdmin(TravelFormStatus status);
    List<TravelForm> findByEmpId(Long id);
    List<TravelForm> findByManagerId(Long managerId);
    List<TravelForm> findAllTravelForm();
    List<TravelForm> toFindAll();
    List<TravelForm> getAllTravelFormsByEmployeeId(Long employeeId);
    List<TravelForm> getAllTravelFormsForManagerByStatus(Long id, TravelFormStatus status);
    List<TravelForm> getAllTravelFormsForAdminWithoutStatus();
    List<TravelForm> findAllTravelFormsByBatchId(Long batchId);
    List<TravelForm> findAllByEmployeeDetails(EmployeeDetails employeeDetailsId);
    List<TravelForm> findAllByStatus(TravelFormStatus status);
    List<TravelForm> findAllTravelFormForAdminByRoles(Long id);
}

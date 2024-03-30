package com.reimbursement.project.repository.service;

import com.reimbursement.project.entity.EmployeeDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeDetailsRepoService {

    List<Map<String,Object>> getColleagues();

    EmployeeDetails toSave(EmployeeDetails employeeDetails);

    Optional<EmployeeDetails> toFindByEmpId(Long empId);

    boolean doesEmailExists(String userEmail);

    Optional<EmployeeDetails> findByEmail(String email);

    EmployeeDetails saveUserData(EmployeeDetails employeeDetails);

    boolean doesEmpIdExists(Long empId);

    Optional<EmployeeDetails> findByEmpId(Long employeeId);

    Optional<EmployeeDetails> findManager(Long managerId);

    List<EmployeeDetails> toFindAll();

    List<EmployeeDetails> findAllEmployees();

    List<Map<String, Object>> getAllEmployees();

    Optional<EmployeeDetails> findById(Long id);

    Optional<EmployeeDetails> findManagerEmpId(Long id);
}

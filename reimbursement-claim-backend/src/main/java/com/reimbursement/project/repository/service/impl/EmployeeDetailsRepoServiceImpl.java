package com.reimbursement.project.repository.service.impl;

import com.reimbursement.project.constant.Constant;
import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.repository.EmployeeDetailsRepository;
import com.reimbursement.project.repository.service.EmployeeDetailsRepoService;
import com.reimbursement.project.service.impl.UserDetailsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsRepoServiceImpl implements EmployeeDetailsRepoService, UserDetailsService {

    private final EmployeeDetailsRepository employeeDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<EmployeeDetails> userDetails = employeeDetailsRepository.findByEmail(email);
        return userDetails.map(UserDetailsInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException(Constant.USER_NOT_FOUND + email));
    }

    @Override
    public boolean doesEmailExists(String userEmail) {
        return employeeDetailsRepository.existsByEmail(userEmail);
    }

    @Override
    public Optional<EmployeeDetails> findByEmail(String email) {
        return employeeDetailsRepository.findByEmail(email);
    }

    @Override
    public EmployeeDetails saveUserData(EmployeeDetails employeeDetails) {
        return employeeDetailsRepository.save(employeeDetails);
    }

    @Override
    public boolean doesEmpIdExists(Long empId) {
        return employeeDetailsRepository.existsByEmpId(empId);
    }

    @Override
    public Optional<EmployeeDetails> findByEmpId(Long employeeId) {
        return employeeDetailsRepository.findByEmpId(employeeId);
    }

    @Override
    public Optional<EmployeeDetails> findManager(Long managerId) {
        return employeeDetailsRepository.findByEmpId(managerId);
    }

    @Override
    public List<Map<String, Object>> getColleagues() {
        return employeeDetailsRepository.findAllEmpIdAndNameAndProfileAndEmail();
    }

    @Override
    public EmployeeDetails toSave(EmployeeDetails employeeDetails) {
        return employeeDetailsRepository.save(employeeDetails);
    }

    @Override
    public Optional<EmployeeDetails> toFindByEmpId(Long empId) {
        return employeeDetailsRepository.findByEmpId(empId);
    }

    @Override
    public List<EmployeeDetails> toFindAll() {
        return employeeDetailsRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    @Override
    public List<EmployeeDetails> findAllEmployees() {
        return employeeDetailsRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> getAllEmployees() {
        return employeeDetailsRepository.findAllEmpIdAndNameAndEmail();
    }

    @Override
    public Optional<EmployeeDetails> findById(Long id) {
        return employeeDetailsRepository.findById(id);
    }

    @Override
    public Optional<EmployeeDetails> findManagerEmpId(Long id) {
        return employeeDetailsRepository.findByManagers_Id(id);
    }
}

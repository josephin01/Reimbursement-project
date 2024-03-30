package com.reimbursement.project.repository;

import com.reimbursement.project.entity.EmployeeDetails;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import com.reimbursement.project.entity.TravelForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TravelFormRepository extends JpaRepository<TravelForm,Long>{

        List<TravelForm> findByEmployeeDetails_EmpIdAndApplyDateBetweenAndEmployeeDetails_FirstName(Long empId, Date fromDate, Date toDate, String firstName);

        @Query(value = "SELECT travel_form.travel_form_status, COUNT(*) as travel_form_status_count FROM travel_form GROUP BY travel_form.travel_form_status", nativeQuery = true)
        List<Map<String, Object>> getTravelFormStatusCounts();
        TravelForm save(TravelForm travelForm);
        List<TravelForm> findByIdIn(List<Long> travelFormId);
        List<TravelForm> findByIdInOrderByCreatedAtDesc(List<Long> travelFormId);
        List<TravelForm> findAllByManagers_IdOrderByCreatedAtDesc(Long managerId);
        List<TravelForm> findAllByEmployeeDetails_EmpIdAndTravelFormStatusOrderByCreatedAtDesc(Long empId, TravelFormStatus travelFormStatus);
        @Query(value = "SELECT travel_form.bill_status, COUNT(*) as bill_status_count FROM travel_form GROUP BY travel_form.bill_status", nativeQuery = true)
        List<Map<String, Object>> getBillsStatusCount();
        Optional<TravelForm> findById(Long id);
        List<TravelForm> findAllByManagers_IdAndTravelFormStatusOrderByCreatedAtDesc(Long managerId, TravelFormStatus travelFormStatus);
        List<TravelForm> findAllByEmployeeDetails_EmpIdOrderByCreatedAtDesc(Long empId);
        List<TravelForm> findAllByEmployeeDetails_IdOrderByCreatedAtDesc(Long id);
        List<TravelForm> findAllByBatch_IdOrderByCreatedAtDesc(Long batchId);
        List<TravelForm> findAllByBatch_Id(Long batchId);
        List<TravelForm> findAllByEmployeeDetails(EmployeeDetails employeeDetailsId);
        List<TravelForm> findAllByTravelFormStatusOrderByCreatedAtDesc(TravelFormStatus status);
        List<TravelForm> findAllByOrderByCreatedAtDesc();
        @Query("SELECT tf FROM TravelForm tf WHERE tf.employeeDetails.id IN " +
            "(SELECT ed.id FROM EmployeeDetails ed JOIN ed.role r WHERE r.id = :id ORDER BY ed.createdAt)")

        List<TravelForm> findAllByEmployeeDetails_Role_IdOrderByCreatedAt(Long id);

        List<TravelForm> findAllByEmployeeDetailsOrderByCreatedAtDesc(EmployeeDetails employeeDetailsId);

}

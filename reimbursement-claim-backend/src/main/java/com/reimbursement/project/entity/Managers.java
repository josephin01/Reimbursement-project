package com.reimbursement.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Managers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String toString() {
        return "Managers{" +
                "id=" + id +
                ", employeeDetails=" + employeeDetails.getId() +
//                ", managerName='" + managerName + '\'' +
//                ", travelForm=" + travelForm +
//                ", expenseClaims=" + expenseClaims +
//                ", projects=" + projects +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                ", deletedAt=" + deletedAt +
                '}';
    }

    @OneToOne
    @JsonIgnoreProperties("managers")
    private EmployeeDetails employeeDetails;

    private String managerName;

    @OneToMany(mappedBy = "managers")
    @JsonIgnore
    private List<TravelForm> travelForm;

    @OneToMany(mappedBy = "managers")
    @JsonIgnore
    private List<ExpenseClaims> expenseClaims;

    @OneToMany(mappedBy = "managers")
    @JsonIgnore
    private List<Projects> projects;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private Date deletedAt;

    @Override
    public int hashCode() {
        return Objects.hash();
    }

}

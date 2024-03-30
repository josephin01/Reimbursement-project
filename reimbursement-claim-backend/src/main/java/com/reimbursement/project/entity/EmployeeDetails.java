package com.reimbursement.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class EmployeeDetails  {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empId")
    private Long empId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="dob")
    private Date dob;

    @Column(name="profile")
    private String profile;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private Managers managers;

    @JsonIgnore
    @OneToMany(mappedBy = "employeeDetails")
    private List<Session> sessions;

    @JsonIgnore
    @OneToMany(mappedBy = "employeeDetails")
    private List<TravelForm> travelForm;

    @Column(name = "isManager")
    private Boolean isManager;

    @JsonIgnore
    @ManyToOne
    private Roles role;


    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "id=" + id +
                ", empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                ", profile='" + profile + '\'' +
//                ", managers=" + managers.getId() +
                ", isManager=" + isManager +
                ", role=" + role+
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    @CreatedDate
    @Column(name = "createdAt")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @Column(name = "deletedAt")
    private Timestamp deletedAt;

}

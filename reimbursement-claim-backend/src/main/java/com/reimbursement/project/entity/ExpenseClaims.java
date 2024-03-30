package com.reimbursement.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.reimbursement.project.entity.Enum.ExpenseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class ExpenseClaims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonDeserialize
    private ExpenseType expenseType;
    @ManyToOne
    private EmployeeDetails employeeDetails;
    @ManyToOne
    private Managers managers;
    @OneToOne
    private Notification notification;
    @ManyToOne
    @JsonIgnoreProperties("expenseClaims")
    private Projects projects;
    private String expenseDescription;
    private Date expenseDate;
    @JsonProperty("Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date applyDate;
    private String colleagueDetails;
    @JsonCreator
    public ExpenseClaims(@JsonProperty("colleagueDetails") String colleagueDetails) {
        this.colleagueDetails = colleagueDetails;
    }
    @OneToMany(mappedBy = "expenseClaims")
    private List<Bills> bills;
    private Float expenseAmount;
    private ExpenseStatus expenseStatus = ExpenseStatus.PENDING;

    @CreatedDate
    private Date createdAt;
    @Override
    public String toString() {
        return "ExpenseClaims{" +
                "id=" + id +
                ", expenseType=" + expenseType.getId() +
                ", managers=" + managers.getId() +
                ", notification=" + notification.getId() +
                ", projects=" + projects.getId() +
                ", expenseDescription='" + expenseDescription + '\'' +
                ", expenseDate=" + expenseDate +
                ", applyDate=" + applyDate +
                ", expenseAmount=" + expenseAmount +
                ", expenseStatus=" + expenseStatus +
                ", createdAt=" + createdAt +
                ", employee=" + employeeDetails.getId() +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
    @LastModifiedDate
    private Date updatedAt;
    private Date deletedAt;
    private String remarks;
}

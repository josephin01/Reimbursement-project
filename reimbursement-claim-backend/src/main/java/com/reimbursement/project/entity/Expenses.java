package com.reimbursement.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JsonIgnore
    private ExpenseType expenseType;

    @ManyToOne
    @JsonIgnore
    private TravelForm travelForm;

    @Column(name = "expenseDescription")
    private String expenseDescription;

    @Column(name = "expenseDate")
    private Date expenseDate;

    @OneToMany(mappedBy = "expenses",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bills> bills;

    @Column(name = "expenseAmount")
    private Float expenseAmount;

    private ExpenseStatus expenseStatus;

    @ManyToOne
    @JsonIgnore
    private Notification notification;

    private Date applyDate;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private Date deletedAt;

}


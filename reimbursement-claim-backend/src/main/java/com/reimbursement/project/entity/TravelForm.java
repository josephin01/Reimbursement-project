package com.reimbursement.project.entity;

import com.fasterxml.jackson.annotation.*;
import com.reimbursement.project.entity.Enum.BillStatus;
import com.reimbursement.project.entity.Enum.TravelFormStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "travel_form")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TravelForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private EmployeeDetails employeeDetails;

    @ManyToOne
    private Managers managers;

    @ManyToOne
    private Projects project;

    private Integer numberOfPeople;

    @OneToMany(mappedBy = "travelForm")
    @JsonIgnore
    private List<Expenses> expenses;

    @ManyToOne
    private PurposeOfVisit purposeOfVisit;

    private String description;
    @JsonProperty("Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date applyDate;
    private Date travelDate;
    private BillStatus billStatus;
    private String remarks;
    private TravelFormStatus travelFormStatus;
    private String colleagueDetails;
    @JsonCreator
    public TravelForm(@JsonProperty("colleagueDetails") String colleagueDetails) {

        this.colleagueDetails = colleagueDetails;
    }
    @OneToOne
    private Notification notification;
    @ManyToOne
    @JsonIgnore
    private Batches batch;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    private Date deletedAt;
    @Override
    public String toString() {
        return "TravelForm{" +
                "id=" + id +
                ", employeeDetails=" + employeeDetails.getId() +
                ", managers=" + managers.getId() +
                ", project=" + project.getId() +
                ", numberOfPeople=" + numberOfPeople +
                ", expenses=" + expenses +
                ", purposeOfVisit=" + purposeOfVisit +
                ", description='" + description + '\'' +
                ", applyDate=" + applyDate +
                ", travelDate=" + travelDate +
                ", billStatus=" + billStatus +
                ", remarks='" + remarks + '\'' +
                ", travelFormStatus=" + travelFormStatus +
                ", colleagueDetails='" + colleagueDetails + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", notification=" + notification +
                ", batch=" + batch +
                '}';
    }
}

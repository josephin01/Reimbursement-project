package com.reimbursement.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String projectName;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<TravelForm> travelForm;

    @Override
    public String toString() {
        return "Projects{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
//                ", travelForm=" + travelForm +
                ", expenseClaims=" + expenseClaims +
                ", managers=" + managers.getId() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    @OneToMany(mappedBy = "projects")
    private List<ExpenseClaims> expenseClaims;

    @ManyToOne
    private Managers managers;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private Date deletedAt;

}

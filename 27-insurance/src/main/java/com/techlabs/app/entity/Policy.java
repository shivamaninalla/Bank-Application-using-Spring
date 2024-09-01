package com.techlabs.app.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private InsurancePlan plan;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private Set<Commission> commissions;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String status;  // e.g., ACTIVE, EXPIRED, CANCELED

    private LocalDate nextPaymentDate;
    
    @Column(nullable = false)
    private Double commissionRate;

    // Getters and Setters
}

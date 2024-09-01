package com.monocept.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "insurance_policies")
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "insurance_plan_id", nullable = false)
    private InsurancePlan insurancePlan;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double premium;

    @Column(nullable = false)
    private Double coverageAmount;

    @Column(nullable = false)
    private Integer term;  // Duration in years

    private String eligibilityCriteria;

    // Getters and Setters
}

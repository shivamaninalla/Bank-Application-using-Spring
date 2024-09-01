package com.techlabs.app.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "insurance_schemes")
public class InsuranceScheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceSchemeId;

    @Column(nullable = false)
    private String insuranceScheme;

    @Lob
    private byte[] schemeImage;

    private double newRegistrationCommission;
    
    private double installmentPaymentCommission;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description; 

    @ManyToOne
    @JoinColumn(name = "insurance_type_id")
    private InsuranceType insuranceType;

    @OneToOne(mappedBy = "insuranceScheme", cascade = CascadeType.ALL)
    private InsurancePlan insurancePlan;
}
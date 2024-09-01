package com.techlabs.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "insurance_types")
public class InsuranceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceTypeId;

    @Column(nullable = false)
    private String name;
    
    private boolean active;

    @OneToMany(mappedBy = "insuranceType", cascade = CascadeType.ALL)
    private List<InsuranceScheme> insuranceSchemes;
}
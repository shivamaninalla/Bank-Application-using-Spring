package com.techlabs.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tax_settings")
public class TaxSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taxId;

//    @ManyToOne
//    @JoinColumn(name = "state_id", nullable = false)
//    private State state;

    @Column(nullable = false)
    private double taxPercentage;
//    
//    private String description;

    private LocalDateTime updatedAt;

}
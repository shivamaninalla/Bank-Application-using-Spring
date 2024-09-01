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
@Table(name = "tax_settings")
public class TaxSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taxId;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(nullable = false)
    private Double taxRate;

    private String description;

    // Getters and Setters
}



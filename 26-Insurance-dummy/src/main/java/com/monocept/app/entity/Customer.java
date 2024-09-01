package com.monocept.app.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @Column(nullable = false)
    private LocalDate dob;

    private String address;
    private String phoneNumber;
    private String bankAccountDetails;
    private String status;  // e.g., ACTIVE, INACTIVE

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = true)
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee verifiedBy;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    // Getters and Setters
}

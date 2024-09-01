package com.monocept.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private Double commissionRate;
    private String status;  // e.g., ACTIVE, INACTIVE

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee createdBy;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToOne
    @JoinColumn(name = "creation_status_id")
    private CreationStatus creationStatus;

    // Getters and Setters
}

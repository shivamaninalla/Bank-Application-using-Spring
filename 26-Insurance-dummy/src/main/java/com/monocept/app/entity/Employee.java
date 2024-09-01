package com.monocept.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String phoneNumber;
    private String address;
    private String position;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agent> createdAgents;

    @OneToMany(mappedBy = "verifiedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> verifiedCustomers;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToOne
    @JoinColumn(name = "creation_status_id")
    private CreationStatus creationStatus;

    // Getters and Setters
}



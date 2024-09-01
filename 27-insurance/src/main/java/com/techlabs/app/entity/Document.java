package com.techlabs.app.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private String documentName;

    @Column(nullable = false)
    private String documentPath;  

    @Column(nullable = false)
    private LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = true)
    private Agent agent;

    // Getters and Setters
}

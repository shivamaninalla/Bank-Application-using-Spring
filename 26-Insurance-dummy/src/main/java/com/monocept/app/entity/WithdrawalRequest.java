package com.monocept.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawal_requests")
public class WithdrawalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long withdrawalRequestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String requestType;  // e.g., CUSTOMER_WITHDRAWAL, AGENT_COMMISSION_WITHDRAWAL

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    private String status;  // e.g., PENDING, APPROVED, REJECTED

    // Getters and Setters
}

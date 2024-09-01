package com.techlabs.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "withdrawal_requests")
public class WithdrawalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long withdrawalRequestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String requestType;  // e.g., CUSTOMER_WITHDRAWAL, AGENT_COMMISSION_WITHDRAWAL

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    private String status;  // e.g., PENDING, APPROVED, REJECTED

    // Getters and Setters
}

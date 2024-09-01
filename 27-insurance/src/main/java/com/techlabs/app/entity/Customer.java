package com.techlabs.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private int age;
    private String phoneNumber;

//    @ManyToOne
//    @JoinColumn(name = "address_id", nullable = false)
//    private Address address;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
    
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    private boolean isActive;

    // Getters and Setters
}

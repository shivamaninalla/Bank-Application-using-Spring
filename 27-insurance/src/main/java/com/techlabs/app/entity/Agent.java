package com.techlabs.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long agentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String phoneNumber;
    
//    @ManyToOne
//    @JoinColumn(name = "address_id", nullable = false)
//    private Address address;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
    

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private Set<Customer> customers;

    private boolean isActive;
    
    
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private Set<Commission> commissions;
    
    
//    private int commissionRate;

}

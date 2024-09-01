package com.techlabs.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;    

//    @ManyToOne
//    @JoinColumn(name = "address_id", nullable = false)
//    private Address address;
  
    private boolean isActive;

    // Getters and Setters
}

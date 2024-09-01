//package com.techlabs.app.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//@Entity
//@Table(name = "addresses")
//@Data
//public class Address {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long addressId;
//
////    private String address;
//
//    private long pincode;
//
//    @ManyToOne
//    @JoinColumn(name = "state_id", nullable = false)    
//    private State state;
//
////    @OneToOne
////    @JoinColumn(name = "user_id", nullable = false)
////    private User user;
//
//    // Getters and Setters
//}

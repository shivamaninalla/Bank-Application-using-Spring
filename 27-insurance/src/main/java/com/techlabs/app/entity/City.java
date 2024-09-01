package com.techlabs.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city_name;

//    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)    
    private State state;

    // Getters and Setters
}

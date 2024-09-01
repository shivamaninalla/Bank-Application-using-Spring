//package com.techlabs.app.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import java.util.Set;
//
//@Entity
//@Data
//@Table(name = "insurance_plans")
//public class InsurancePlan {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long insuranceTypeId;
//
//    @Column(nullable = false)
//    private String name;
//    
//    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
//    private Set<Policy> policies;
//
//    // Getters and Setters
//}

package com.techlabs.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "insurance_plans")
public class InsurancePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceId;

    @OneToOne
    @JoinColumn(name = "insurance_scheme_id")
    private InsuranceScheme insuranceScheme;

    private int minimumPolicyTerm;
    private int maximumPolicyTerm;
    private int minimumAge;
    private int maximumAge;
    private double minimumInvestmentAmount;
    private double maximumInvestmentAmount;
    private double profitRatio;

    private boolean active;
}
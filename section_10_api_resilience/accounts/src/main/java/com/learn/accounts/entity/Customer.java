package com.learn.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
// This annotation show that create all args constructor for class for each field
@AllArgsConstructor
//This annotation show that create no argument constructor for class because when we create args constucture it not created by spring but when we not create args constuctor spring by default create it.
@NoArgsConstructor
public class Customer extends BaseEntity {

//	tell id should primary key
    @Id
//  Create automatically create id for upcoming rows
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    
    

}

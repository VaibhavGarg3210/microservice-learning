package com.learn.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.accounts.entity.Customer;

@Repository
//Jpa Respository is come from spring data jpa repository
// JPA Repo extends list,paging and sorting repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);


}

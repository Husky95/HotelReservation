package com.skillstorms.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorms.backend.model.Customer;

@Repository
public interface  CustomerRepository extends JpaRepository<Customer, Integer> {

}

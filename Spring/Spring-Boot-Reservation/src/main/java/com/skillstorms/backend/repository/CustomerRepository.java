package com.skillstorms.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.skillstorms.backend.model.Customer;
//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete
public interface  CustomerRepository extends CrudRepository<Customer, Integer> {

}

package com.skillstorms.backend.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.repository.CustomerRepository;

@RestController // @RestController = @Controller + @ResponseBody
@CrossOrigin("*") // If you don't like CorsFilter, you're in luck. They do the same thing
@RequestMapping(path="/customer") // This means URL's start with /demo (after Application path)
public class CustomerController {
  @Autowired 
  private CustomerRepository customerRepository;

  @PostMapping
  public Customer create(@Valid @RequestBody Customer customer) {
	 System.out.println("Customer add call"); 
	 return customerRepository.save(customer);
  }  
  //@GetMapping(path="/all")
  //public Page<Customer> getAllCustomer(Pageable pageable) {
      //return customerRepository.findAll(pageable);
  //}
  
  @GetMapping(path="/all")
  public @ResponseBody Iterable<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }
  @GetMapping(path="/{id}")
  public Customer findByID(@PathVariable (value = "id") int customerID) {
	  	Optional<Customer> customer = customerRepository.findById(customerID);
		return customer.isPresent() ? customer.get() : null;
  }
  
}
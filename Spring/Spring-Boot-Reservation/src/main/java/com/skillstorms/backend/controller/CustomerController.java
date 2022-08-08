package com.skillstorms.backend.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.model.Hotel;
import com.skillstorms.backend.model.ResourceNotFoundException;
import com.skillstorms.backend.repository.CustomerRepository;

@RestController
@CrossOrigin("*")
@RequestMapping(path="/customer") 
public class CustomerController {
  @Autowired 
  private CustomerRepository customerRepository;

  /**
   * Method handle POST request for creating a single Customer object.
   * @param customer Customer object from front end.
   * @return The created Customer object in database.
   */
  @PostMapping
  public Customer create(@Valid @RequestBody Customer customer) {
	 System.out.println("Customer add call"); 
	 return customerRepository.save(customer);
  }  
  
  /**
   * Method handle GET request for getting all the Customer objects.
   * @return All the Customer objects in database.
   */
  @GetMapping(path="/all")
  public @ResponseBody Iterable<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }
  
  /**
   * Method handle GET request for getting a Customer objects by CustomerID.
   * @param customerID CustomerID value from the front end.
   * @return The Customer objects where Customer_Id in database equal to the pass in CustomerID.
   */
  @GetMapping(path="/{id}")
  public Customer findByID(@PathVariable (value = "id") int customerID) {
	  	Optional<Customer> customer = customerRepository.findById(customerID);
		return customer.isPresent() ? customer.get() : null;
  }
  
  /**
   * Method handle PUT request for updating a Customer objects by CustomerID.
   * @param customerID CustomerID value from the front end.
   * @param newCustomer new Customer object value from the front end.
   * @return The updated Customer objects where Customer_Id in database equal to the pass in CustomerID.
   */
  @PutMapping(path="/{id}")
  public @ResponseBody Optional<Object> update(@PathVariable (value = "id") int customerID, @Valid @RequestBody Customer newCustomer) { 
	  return customerRepository.findById(customerID)
		      .map(customer -> {
		    	  customer.setFirstName(newCustomer.getFirstName());
		    	  customer.setLastName(newCustomer.getLastName());
		    	  customer.setEmail(newCustomer.getEmail());
		    	  customer.setStreet(newCustomer.getStreet());
		    	  customer.setCity(newCustomer.getCity());
		    	  customer.setState(newCustomer.getState());
		    	  customer.setZipcode(newCustomer.getZipcode());
		    	  customer.setPhone(newCustomer.getPhone());
		    	  customer.setState(newCustomer.getState());
		          return customerRepository.save(customer);
	 });
  }
  
  /**
   * Method handle DELETE request for deleting a Customer objects by CustomerID.
   * @param customerID CustomerID value from the front end.
   * @return The deleted Customer objects where Customer_Id in database equal to the pass in CustomerID.
   */
  @DeleteMapping(path="/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public void delete(@PathVariable int id) {
	  customerRepository.deleteById(id);
  } 
}
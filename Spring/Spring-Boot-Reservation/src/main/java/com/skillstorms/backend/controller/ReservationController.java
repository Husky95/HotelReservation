package com.skillstorms.backend.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.model.Hotel;
import com.skillstorms.backend.model.Reservation;
import com.skillstorms.backend.repository.CustomerRepository;
import com.skillstorms.backend.repository.HotelRepository;
import com.skillstorms.backend.repository.ReservationRepository;
import com.skillstorms.backend.model.ResourceNotFoundException;


@RestController // @RestController = @Controller + @ResponseBody
@CrossOrigin("*") // If you don't like CorsFilter, you're in luck. They do the same thing
@RequestMapping(path="/reservations") // This means URL's start with /demo (after Application path)
public class ReservationController {
  @Autowired 
  private ReservationRepository reservationRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private HotelRepository hotelRepository;
  
  @PostMapping("/customer/{customerId}")
  //@PostMapping("/customer/{customerId}/hotel/{hotelID}")
  //public Reservation create(@PathVariable (value = "customerId") Integer customerId,@PathVariable (value = "hotelID") Integer hotelID, @Valid @RequestBody Reservation reservation) {
  public Reservation create(@PathVariable (value = "customerId") Integer customerId, @Valid @RequestBody Reservation reservation) {

	System.out.println("reservation add");
	return customerRepository.findById(customerId).map(customer -> {
        reservation.setCustomer(customer);
        return reservationRepository.save(reservation);
	}).orElseThrow(() -> new ResourceNotFoundException("Customer Id " + customerId + " not found"));

	//return hotelRepository.findById(hotelID).map(hotel -> {
		//temp.setHotel(hotel);
        //return reservationRepository.save(temp);
	//}).orElseThrow(() -> new ResourceNotFoundException("Customer Id " + hotelID + " not found"));
  }

  @GetMapping(path="")
  public @ResponseBody Iterable<Reservation> getAllCustomers() {
    // This returns a JSON or XML with the users
    return reservationRepository.findAll();
  }
  //TODO 
  //update
  @PutMapping(path="")
  public @ResponseBody Reservation update(@Valid @RequestBody Reservation reservation) {
  	Optional<Reservation> reservationDatabaseVersion = reservationRepository.findById(reservation.getReservationNumber());
  	if (reservationDatabaseVersion.isPresent()) {
  		return reservationRepository.save(reservation);
  	}
  	else {
  		throw new ResourceNotFoundException();
  	}
  }
  //delete
  @DeleteMapping(path="/{id}")
  public void delete(@PathVariable int id) {
  	Optional<Reservation> reservationStagedForDelete = reservationRepository.findById(id);
  	if (reservationStagedForDelete.isPresent()) {
  		Reservation r = reservationStagedForDelete.get();
  		Customer c = r.getCustomer();
  		c.getReservations().remove(r);
  		customerRepository.save(c);
  		reservationRepository.deleteById(id);
  	}
  	else {
  		throw new ResourceNotFoundException();
  	}
  	
  }
}
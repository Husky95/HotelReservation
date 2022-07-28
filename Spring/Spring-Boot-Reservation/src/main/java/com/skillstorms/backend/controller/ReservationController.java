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

import com.skillstorms.backend.model.Reservation;
import com.skillstorms.backend.repository.CustomerRepository;
import com.skillstorms.backend.repository.HotelRepository;
import com.skillstorms.backend.repository.ReservationRepository;
import com.skillstorms.backend.model.ResourceNotFoundException;


@RestController // @RestController = @Controller + @ResponseBody
@CrossOrigin("*") // If you don't like CorsFilter, you're in luck. They do the same thing
@RequestMapping(path="/reservation") // This means URL's start with /demo (after Application path)
public class ReservationController {
  @Autowired 
  private ReservationRepository reservationRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private HotelRepository hotelRepository;
  
  //@PostMapping("/customer/{customerId}")
  //public Reservation create(@PathVariable (value = "customerId") Integer customerId, @Valid @RequestBody Reservation reservation) {

  
  @PostMapping("/customer/{customerId}/hotel/{hotelID}")
  public Reservation create(@PathVariable (value = "customerId") Integer customerId,
		  					@PathVariable (value = "hotelID") Integer hotelID, 
		  					@Valid @RequestBody Reservation reservation) {

		System.out.println("reservation add");
		customerRepository.findById(customerId).map(customer -> {
	        reservation.setCustomer(customer);
	        return reservation;
	        //return reservationRepository.save(reservation);
		}).orElseThrow(() -> new ResourceNotFoundException("Customer Id " + customerId + " not found"));
	
		return hotelRepository.findById(hotelID).map(hotel -> {
			reservation.setHotel(hotel);
	        return reservationRepository.save(reservation);
		}).orElseThrow(() -> new ResourceNotFoundException("HotelId " + hotelID + " not found"));  
	}

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Reservation> findAll() {
	  	return reservationRepository.findAll();
  }
  
  @GetMapping(path="/{id}")
  public Reservation findByID(@PathVariable (value = "id") int reservationID) {
	  	Optional<Reservation> reservation = reservationRepository.findById(reservationID);
		return reservation.isPresent() ? reservation.get() : null;
  }
  
  //TODO 
  //update
  //delete
}
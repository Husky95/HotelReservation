package com.skillstorms.backend.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.model.DateChecker;
import com.skillstorms.backend.model.Hotel;
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
  @GetMapping(path="/vacancy/{arrivalDate}/{departDate}")
  public @ResponseBody ArrayList<Hotel> findByVacancy(@PathVariable (value = "arrivalDate") String startDate, @PathVariable (value = "departDate") String endDate) {
	  Iterable<Reservation> ReservationList = reservationRepository.findAll();
	  ArrayList<Integer> hotelList = new ArrayList<Integer>();
	  
	  //This go through the reservation list and check if there matching date in arrivalDate or departDate
	  //if there is add the hotelID to a hotel List 
	  //if not return a list of all hotel 
	  ReservationList.forEach(
          (element) -> { 
        	  LocalDate arrivalDate = LocalDate.parse(startDate);
        	  LocalDate departDate = LocalDate.parse(endDate);
        	  
        	  DateChecker checker = new DateChecker(arrivalDate,departDate);
        	  //int compareValue = arrivalDate.compareTo(element.getArrivalDate());

        	  if (checker. checkConflictRange(element.getArrivalDate(), element.getDepartDate())) {
        		  Hotel hotel = element.getHotel();
        		  hotelList.add(hotel.getHotelID());
        		  
        	  	} 
        	  else {
        		  //System.out.println("input date is available");
        	      //ArrayList<Hotel> allHotel = new ArrayList<Hotel>(hotelRepository.findAll());
        	  }
           }
      );
	  
	  //Check if hotelList for matching date > 0 
	  //if not return all list 
	  //if it is go do the logic
	  if( hotelList.size()> 0 )
	  {
		  Set<Integer> hotelSet = new HashSet<>(hotelList);
		  ArrayList<Integer> occurencesList = new ArrayList<Integer>(hotelSet.size());
		  ArrayList<Hotel> vacantHotel = new ArrayList<Hotel>();
		  ArrayList<Hotel> allHotel = new ArrayList<Hotel>(hotelRepository.findAll());

		  //this go through hotelSet with matching date and check the number of hotelID occurences
		  //if the total occurences is less than the hotel max capacity display the hotel 
		  //if it not then remove the hotel from the hotelSet 
		  for (int i = 0 ; i < hotelSet.size(); i++  )
		  {
			  Integer hotelID = hotelSet.iterator().next();
			  occurencesList.add(Collections.frequency(hotelList,hotelID)); 
			  //if occurence is less than capacity < remove it from the list 
			  //if not add it to the list 
			  if( occurencesList.get(i) > 0 )
			  {
				  Optional <Hotel> hotel = hotelRepository.findById(hotelID);
				  Hotel noVacant = hotel.isPresent() ? hotel.get() : null;
				  allHotel.remove(noVacant);
				  hotelSet.remove(hotelID);
			  }
			  else
			  {
				  continue;
				  //Optional <Hotel> hotel = hotelRepository.findById(hotelID);
				  //Hotel vacant = hotel.isPresent() ? hotel.get() : null;
				  //vacantHotel.add(vacant);
			  }
	
		  }
		  System.out.println(allHotel);
		  return allHotel;
	  }
	  else
	  {
		  ArrayList<Hotel> allHotel = new ArrayList<Hotel>(hotelRepository.findAll());
		  return allHotel;
	  }
  }
  
  @GetMapping(path="/{id}")
  public Reservation findByID(@PathVariable (value = "id") int reservationID) {
	  	Optional<Reservation> reservation = reservationRepository.findById(reservationID);
		return reservation.isPresent() ? reservation.get() : null;
  }
  //update
  @PutMapping(path="/{reservationId}/customer/{customerId}/hotel/{hotelId}")
  public @ResponseBody Optional<Object> update(@PathVariable (value = "reservationId") int reservationID, 
		  									   @PathVariable (value = "customerId") int customerID, 
		  									   @PathVariable (value = "hotelId") int hotelID, 
		  									   @Valid @RequestBody Reservation newReservation)
  {
	  System.out.println("Put Reservation");
	  return reservationRepository.findById(reservationID)
		      .map(reservation -> {
		    	  Optional<Customer> tempCustomer = customerRepository.findById(customerID);
		    	  Optional<Hotel> tempHotel = hotelRepository.findById(hotelID);

		    	  Customer currentCustomer  = tempCustomer.isPresent() ? tempCustomer.get() : null;
		    	  Hotel currentHotel  = tempHotel.isPresent() ? tempHotel.get() : null;
		    	  
		    	  System.out.println(currentCustomer);
		    	  reservation.setReserveDate(newReservation.getReserveDate());
		    	  reservation.setArrivalDate(newReservation.getArrivalDate());
		    	  reservation.setDepartDate(newReservation.getDepartDate());
		    	  reservation.setNumAdults(newReservation.getNumAdults());
		    	  reservation.setNumKids(newReservation.getNumKids());
		    	  reservation.setNumBeds(newReservation.getNumBeds());
		    	  reservation.setBedType(newReservation.getBedType());
		    	  reservation.setRoomNumber(newReservation.getRoomNumber());
		    	  reservation.setCustomer(currentCustomer);
		    	  reservation.setHotel(currentHotel);

		          return reservationRepository.save(reservation);
	 });	
  }
  	
    @DeleteMapping(path="/{id}")
    public void delete(@PathVariable (value = "id") int reservationID) {
    	Optional<Reservation> reservationStagedForDelete = reservationRepository.findById(reservationID);
    	if (reservationStagedForDelete.isPresent()) {
    		Reservation r = reservationStagedForDelete.get();
    		Customer c = r.getCustomer();
    		c.getReservations().remove(r);
    		customerRepository.save(c);
    		//Hotel h = r.getHotel();
    		//h.getReservations().remove(r);
    		//hotelRepository.save(h);
    		reservationRepository.deleteById(reservationID);
    	}
    	else {
    		throw new ResourceNotFoundException();
    	}
    }
  }

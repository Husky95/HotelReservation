package com.skillstorms.backend.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.server.ResponseStatusException;

import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.model.DateChecker;
import com.skillstorms.backend.model.Hotel;
import com.skillstorms.backend.model.HotelBookedException;
import com.skillstorms.backend.model.Reservation;
import com.skillstorms.backend.repository.CustomerRepository;
import com.skillstorms.backend.repository.HotelRepository;
import com.skillstorms.backend.repository.ReservationRepository;
import com.skillstorms.backend.service.HotelService;
import com.skillstorms.backend.service.ReservationService;
import com.skillstorms.backend.model.ResourceNotFoundException;

@RestController // @RestController = @Controller + @ResponseBody
@CrossOrigin("*") // If you don't like CorsFilter, you're in luck. They do the same thing
@RequestMapping(path = "/reservation") // This means URL's start with /demo (after Application path)
public class ReservationController {
  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private HotelRepository hotelRepository;
  @Autowired
  private ReservationService reservationService;
  @Autowired
  private HotelService hotelService;
  //@PostMapping("/customer/{customerId}")
  //public Reservation create(@PathVariable (value = "customerId") Integer customerId, @Valid @RequestBody Reservation reservation) {

  @PostMapping("/customer/{customerId}/hotel/{hotelID}")
  public Reservation create(@PathVariable(value = "customerId") Integer customerId,
    @PathVariable(value = "hotelID") Integer hotelID,
    @Valid @RequestBody Reservation reservation) {

    customerRepository.findById(customerId).map(customer -> {
      reservation.setCustomer(customer);
      return reservation;
    }).orElseThrow(() -> new ResourceNotFoundException("Customer Id " + customerId + " not found"));

    return hotelRepository.findById(hotelID).map(hotel -> {
    	boolean isHotelStillAvailable = hotelService.isHotelAvailable(hotel.getHotelID(), reservation.getArrivalDate(), reservation.getDepartDate());
    	if (isHotelStillAvailable) {
    		reservation.setHotel(hotel);
        return reservationRepository.save(reservation);
    	}
    	else {
    		String message = hotel.getHotelName() + " is no longer available.";
    		throw new ResponseStatusException(HttpStatus.CONFLICT, message, new HotelBookedException(message));
    	}
    }).orElseThrow(() -> new ResourceNotFoundException("HotelId " + hotelID + " not found"));
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable < Reservation > findAll() {
    return reservationRepository.findAll();
  }
  @GetMapping(path = "/vacancy/{arrivalDate}/{departDate}")
  public @ResponseBody ArrayList < Hotel > findByVacancy(@PathVariable(value = "arrivalDate") String startDate, @PathVariable(value = "departDate") String endDate) {
    return reservationService.getVacantHotel(startDate, endDate);
  }

  @GetMapping(path = "/{id}")
  public Reservation findByID(@PathVariable(value = "id") int reservationID) {
    Optional < Reservation > reservation = reservationRepository.findById(reservationID);
    return reservation.isPresent() ? reservation.get() : null;
  }
  //update
  @PutMapping(path = "/{reservationId}/customer/{customerId}/hotel/{hotelId}")
  public @ResponseBody Optional < Object > update(@PathVariable(value = "reservationId") int reservationID,
    @PathVariable(value = "customerId") int customerID,
    @PathVariable(value = "hotelId") int hotelID,
    @Valid @RequestBody Reservation newReservation) {
    return reservationRepository.findById(reservationID)
      .map(reservation -> {
        Optional < Customer > tempCustomer = customerRepository.findById(customerID);
        Optional < Hotel > tempHotel = hotelRepository.findById(hotelID);

        Customer currentCustomer = tempCustomer.isPresent() ? tempCustomer.get() : null;
        Hotel currentHotel = tempHotel.isPresent() ? tempHotel.get() : null;
        
        List<Reservation> reservations = reservationService.findByHotelIDAndDateRagne(hotelID, newReservation.getArrivalDate(), newReservation.getDepartDate());
        Optional<Reservation> oldReservation = reservationRepository.findById(reservationID);
        if (oldReservation.isPresent()) {
        	reservations.remove(oldReservation.get());
        }
        if (tempHotel.isPresent()) {
        	 boolean isNewReservationAvailable = hotelService.isHotelAvailable(tempHotel.get(), reservations);
        	 if (isNewReservationAvailable) {
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
        	 }
        	 else {
        		 String message = tempHotel.get().getHotelName() + " is not available for the new dates.";
        		 throw new ResponseStatusException(HttpStatus.CONFLICT, message, new HotelBookedException(message));
        	 }
        }
        else {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

      });
  }

  @DeleteMapping(path = "/{id}")
  public void delete(@PathVariable(value = "id") int reservationID) {
    Optional < Reservation > reservationStagedForDelete = reservationRepository.findById(reservationID);
    if (reservationStagedForDelete.isPresent()) {
      Reservation r = reservationStagedForDelete.get();
      Customer c = r.getCustomer();
      c.getReservations().remove(r);
      customerRepository.save(c);
      //Hotel h = r.getHotel();
      //h.getReservations().remove(r);
      //hotelRepository.save(h);
      reservationRepository.deleteById(reservationID);
    } else {
      throw new ResourceNotFoundException();
    }
  }
}
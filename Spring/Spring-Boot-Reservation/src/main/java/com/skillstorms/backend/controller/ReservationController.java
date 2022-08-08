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
import com.skillstorms.backend.service.HotelService;
import com.skillstorms.backend.service.ReservationService;
import com.skillstorms.backend.model.ResourceNotFoundException;

@RestController
@CrossOrigin("*") 
@RequestMapping(path = "/reservation") 
public class ReservationController {
  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private HotelRepository hotelRepository;
  @Autowired
  private ReservationService reservationService;
 

  /**
   * Method handle POST request for creating a single reservation.
   * @param customerId Id of customer that create this reservation.
   * @param hotelID Id of the hotel that was reserve.
   * @param reservation Reservation object from front end.
   * @return The created Reservation objects in database.
   */
  @PostMapping("/customer/{customerId}/hotel/{hotelID}")
  public Reservation create(@PathVariable(value = "customerId") Integer customerId,
    @PathVariable(value = "hotelID") Integer hotelID,
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

  /**
   * Method handle GET request for getting all the Reservation objects.
   * @return All the Reservation objects in database.
   */
  @GetMapping(path = "/all")
  public @ResponseBody Iterable < Reservation > findAll() {
    return reservationRepository.findAll();
  }
  
  /**
   * Method handle GET request returning all hotel with vacant room (no full reservation)
   * @param startDate startDate of the the reservation.
   * @param endDate endDate of the reservation.
   * @return All the Reservation objects in database.
   */
  @GetMapping(path = "/vacancy/{arrivalDate}/{departDate}")
  public @ResponseBody ArrayList < Hotel > findByVacancy(@PathVariable(value = "arrivalDate") String startDate, @PathVariable(value = "departDate") String endDate) {
    return reservationService.getVacantHotel(startDate, endDate);
  }

  /**
   * Method handle GET request for a Reservation objects by ID.
   * @param reservationID Id of reservation that the front-end want.
   * @return A Reservation objects in database by the input ID.
   */
  @GetMapping(path = "/{id}")
  public Reservation findByID(@PathVariable(value = "id") int reservationID) {
    Optional < Reservation > reservation = reservationRepository.findById(reservationID);
    return reservation.isPresent() ? reservation.get() : null;
  }

  /**
   * Method handle PUT request for updating a single reservation.
   * @param customerId Id of customer that create this reservation.
   * @param hotelID Id of the hotel that was reserve.
   * @param reservation The new Reservation object from front end.
   * @return The updated Reservation objects in database.
   */
  @PutMapping(path = "/{reservationId}/customer/{customerId}/hotel/{hotelId}")
  public @ResponseBody Optional < Object > update(@PathVariable(value = "reservationId") int reservationID,
    @PathVariable(value = "customerId") int customerID,
    @PathVariable(value = "hotelId") int hotelID,
    @Valid @RequestBody Reservation newReservation) {
    System.out.println("Put Reservation");
    return reservationRepository.findById(reservationID)
      .map(reservation -> {
        Optional < Customer > tempCustomer = customerRepository.findById(customerID);
        Optional < Hotel > tempHotel = hotelRepository.findById(hotelID);

        Customer currentCustomer = tempCustomer.isPresent() ? tempCustomer.get() : null;
        Hotel currentHotel = tempHotel.isPresent() ? tempHotel.get() : null;

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
  /**
   * Method handle DELETE request for deleting a Reservation objects byReservationID.
   * @param ReservationID ReservationID value from the front end.
   * @return The deleted Reservation objects where CReservation_Id in database equal to the pass in ReservationID.
   */
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
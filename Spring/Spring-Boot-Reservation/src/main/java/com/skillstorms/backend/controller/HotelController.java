package com.skillstorms.backend.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorms.backend.model.CityState;
import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.model.Hotel;
import com.skillstorms.backend.model.ResourceNotFoundException;
import com.skillstorms.backend.repository.HotelRepository;
import com.skillstorms.backend.service.HotelService;

@RestController 
@CrossOrigin(origins = "*")
@RequestMapping(path="/hotel")
public class HotelController {
  @Autowired 
  private HotelRepository hotelRepository;
  @Autowired
  private HotelService hotelService;
  
  /**
   * Method handle POST request for creating a single Hotel object.
   * @param customer Hotel object from front end.
   * @return The created Hotel object in database.
   */
  @PostMapping
  public Hotel create(@Valid @RequestBody Hotel hotel) {
	return hotelRepository.save(hotel);
  }

  /**
   * Method handle GET request for getting all the Hotel objects.
   * @return All the Hotel objects in database.
   */
  @GetMapping(path="/all")
  public @ResponseBody Iterable<Hotel> getAllCustomers() {
    return hotelRepository.findAll();
  }
  
  /**
   * Method handle GET request for getting a Hotel objects by HotelID.
   * @param HotelID HotelID value from the front end.
   * @return The Hotel objects where Hotel_Id in database  equal to the pass in HotelID.
   */
  @GetMapping(path="/{hotelId}")
  public Hotel findByID(@PathVariable (value = "hotelId") int hotelID) {
	  	Optional<Hotel> hotel = hotelRepository.findById(hotelID);
		return hotel.isPresent() ? hotel.get() : null;
  }
  
  @GetMapping(path="/statecity")
  public @ResponseBody ArrayList<CityState> getStateAndCity() {
	  Iterable<Hotel> hotel = hotelRepository.findAll();
	    HashMap<String, String> cities = new HashMap<String, String>();
      int n = 10; 
	  ArrayList<CityState> arr = new ArrayList<CityState>(n);	  
	  hotel.forEach((element) -> { 
		  CityState temp = new CityState();
		  String city = element.getCity();
		  String state = element.getState();
		  String key = city+state;
		  if ( cities.put(key, city) == null) {
			  temp.setCity(city);
			  temp.setState(state);
			  arr.add(temp);
		  }
	  });
	  return arr;
  }
  
  @GetMapping(path="/name/{name}")
  public @ResponseBody List<Hotel> findByName(@PathVariable String name) {
  	return hotelRepository.findByHotelName(name);
  }
  
  /**
   * Method handle PUT request for updating a Hotel objects by HotelID.
   * @param HotelID HotelID value from the front end.
   * @param newHotel Hotel object from the front end to be updated.
   * @return The updated Hotel objects where Hotel_Id in database equal to the pass in HotelID.
   */
  @PutMapping(path="/{id}")
  public @ResponseBody Optional<Object> update(@PathVariable (value = "id") int hotelID, @Valid @RequestBody Hotel newHotel) { 
	  return hotelRepository.findById(hotelID)
		      .map(hotel -> {
		    	  hotel.setHotelName(newHotel.getHotelName());
		    	  hotel.setCity(newHotel.getCity());
		    	  hotel.setStreet(newHotel.getStreet());
		    	  hotel.setState(newHotel.getState());
		    	  hotel.setZipcode(newHotel.getZipcode());
		    	  hotel.setPhone(newHotel.getPhone());
		    	  hotel.setTotalRoom(newHotel.getTotalRoom());
		    	  hotel.setRating(newHotel.getRating());
		    	  hotel.setPrice(newHotel.getPrice());
		    	  hotel.setDescription(newHotel.getDescription());
		          return hotelRepository.save(hotel);
	 });
  }
  /**
   * Method handle DELETE request for deleting a Hotel objects by HotelID.
   * @param id HotelID value from the front end.
   * @return The deleted Hotel objects where Hotel_Id in database equal to the pass in HotelID.
   */
  @DeleteMapping(path="/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public void delete(@PathVariable int id) {
  	hotelRepository.deleteById(id);
  }
  
  @GetMapping(path="/location")
  public @ResponseBody List<Hotel> findHotelsByCityAndState(
  		@RequestParam(name="city", defaultValue="") String city, 
  		@RequestParam(name="state", defaultValue="") String state) {
  	return hotelService.findByCityAndState(city, state);
  }
  
  @GetMapping(path="/search")
  public @ResponseBody List<Hotel> findHotelsByCityStateAndDate(
  		@RequestParam(name="city", defaultValue="") String city, 
  		@RequestParam(name="state", defaultValue="") String state,
  		@RequestParam(name="arrival-date") String arrivalDate,
  		@RequestParam(name="depart-date") String departDate,
  		@RequestParam(name="sort", defaultValue="price") String sortBy,
  		@RequestParam(name="isAsc", defaultValue="true") boolean isAsc,
  		@RequestParam(name="maxPrice") Optional<Float> maxPrice) {
  	LocalDate arrival = LocalDate.parse(arrivalDate);
  	LocalDate depart = LocalDate.parse(departDate);
  	if (maxPrice.isPresent()) {
  		return hotelService.findyByCityStatePriceAndAvailability(city, state, arrival, depart, sortBy, isAsc, maxPrice.get());
  	}
  	else {
  		return hotelService.findByCityStateAndAvailability(city, state, arrival, depart, sortBy, isAsc);
  	}
  }
  
  @GetMapping(path="/available")
  public @ResponseBody boolean isHotelAvailableDateRange(
  		@RequestParam(name="id") int id,
  		@RequestParam(name="arrival-date") String arrivalDate,
  		@RequestParam(name="depart-date") String departDate) {
  	LocalDate arrival = LocalDate.parse(arrivalDate);
  	LocalDate depart = LocalDate.parse(departDate);
  	return hotelService.isHotelAvailable(id, arrival, depart);
  }
  
  
  
}


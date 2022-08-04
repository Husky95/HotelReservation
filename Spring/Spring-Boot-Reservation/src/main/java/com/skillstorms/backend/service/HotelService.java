package com.skillstorms.backend.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.skillstorms.backend.model.Hotel;
import com.skillstorms.backend.model.Reservation;
import com.skillstorms.backend.model.ReservationCount;
import com.skillstorms.backend.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private ReservationService reservationService;
	
	public List<Hotel> findByCityAndState(String city, String state) {
		return hotelRepository.findByCityAndState(city, state);
	}
	
	public List<Integer> getHotelIDs(List<Hotel> hotels) {
		List<Integer> hotelIDs = new LinkedList<>();
		for (Hotel hotel : hotels) {
			hotelIDs.add(hotel.getHotelID());
		}
		return hotelIDs;
	}
	
	public Map<Integer, Hotel> mapHotels(List<Hotel> hotels) {
		Map<Integer, Hotel> hotelMap = new HashMap<>();
		for (Hotel hotel : hotels) {
			hotelMap.put(hotel.getHotelID(), hotel);
		}
		return hotelMap;
	}
	
	public List<Hotel> findByCityStateAndAvailability(String city, String state, LocalDate arrivalDate, LocalDate departDate, String sortBy, boolean isAsc) {
		Sort.Direction dir = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		List<Hotel> areaHotels = hotelRepository.findByCityAndState(city, state, Sort.by(dir, sortBy));
		List<Hotel> availableHotels = new LinkedList<>();
		for (Hotel areaHotel : areaHotels) {
			List<Reservation> reservations = reservationService.findByHotelIDAndDateRagne(areaHotel.getHotelID(), arrivalDate, departDate);
			if (isHotelAvailable(areaHotel, reservations)) {
				availableHotels.add(areaHotel);
			}
		}
		return availableHotels;
	}
	
	private boolean isHotelAvailable(Hotel hotel, List<Reservation> reservations) {
		return hotel.getTotalRoom() > reservationService.getMaxReservationOverlap(reservations);
	}
	
	public boolean isHotelAvailable(int hotelID, LocalDate arrivalDate, LocalDate departDate) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelID);
		if (hotel.isPresent()) {
			List<Reservation> reservations = reservationService.findByHotelIDAndDateRagne(hotelID, arrivalDate, departDate);
			return isHotelAvailable(hotel.get(), reservations);
		}
		else {
			return false;
		}
	}

}
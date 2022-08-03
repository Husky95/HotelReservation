package com.skillstorms.backend.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Hotel> findByCityStateAndAvailability(String city, String state, LocalDate arrivalDate, LocalDate departDate) {
		List<Hotel> areaHotels = hotelRepository.findByCityAndState(city, state);
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

}
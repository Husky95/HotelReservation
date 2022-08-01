package com.skillstorms.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorms.backend.model.ReservationCount;
import com.skillstorms.backend.repository.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public List<ReservationCount> getReservationCountByHotel(LocalDate arrivalDate, LocalDate departDate) {
		return reservationRepository.countReservationsByHotelForDateRange(arrivalDate, departDate);
	}
	
	public List<ReservationCount> getReservationCountByHotel(LocalDate arrivalDate, LocalDate departDate, List<Integer> hotelIDs) {
		return reservationRepository.countReservationsByHotelForDateRange(arrivalDate, departDate, hotelIDs);
	}

}
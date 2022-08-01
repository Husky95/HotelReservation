package com.skillstorms.backend.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skillstorms.backend.model.Reservation;
import com.skillstorms.backend.model.ReservationCount;

public interface ReservationRepository  extends JpaRepository<Reservation, Integer>{

	/**
	 * 
	 * @param arrivalDate
	 * @param departDate
	 * @return A list of reservation counts where each item in the list has a hotelID and the number of reservations for that hotel between arrivalDate and departDate
	 */
	@Query("SELECT r.hotel AS hotel, COUNT(*) AS count FROM Reservation r "
			+ "WHERE :arrivalDate BETWEEN r.arrivalDate AND r.departDate "
			+ "OR r.arrivalDate BETWEEN :arrivalDate AND :departDate GROUP BY r.hotel")
	List<ReservationCount> countReservationsByHotelForDateRange(@Param("arrivalDate") LocalDate arrivalDate, @Param("departDate") LocalDate departDate);
	
	/**
	 * 
	 * @param arrivalDate
	 * @param departDate
	 * @param hotelIDs - A limited list of hotels to search over. For example, a list of hotels for a given city.
	 * @return A list of reservation counts where each item in the list has a hotelID and the number of reservations for that hotel between arrivalDate and departDate
	 */
	@Query("SELECT r.hotel AS hotel, COUNT(*) AS count FROM Reservation r "
			+ "WHERE r.hotel.hotelID IN :hotelIDs "
			+ "AND (:arrivalDate BETWEEN r.arrivalDate AND r.departDate "
			+ "OR r.arrivalDate BETWEEN :arrivalDate AND :departDate) GROUP BY r.hotel")
	List<ReservationCount> countReservationsByHotelForDateRange(@Param("arrivalDate") LocalDate arrivalDate, @Param("departDate") LocalDate departDate, @Param("hotelIDs") List<Integer> hotelIDs);	
	
}
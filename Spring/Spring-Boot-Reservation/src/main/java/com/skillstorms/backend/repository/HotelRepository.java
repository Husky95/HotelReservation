package com.skillstorms.backend.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorms.backend.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
	List<Hotel> findByHotelName(String hotelName);
	
	List<Hotel> findByCityAndState(String city, String state);
	
	List<Hotel> findByCityAndState(String city, String state, Sort sort);
	
	List<Hotel> findByHotelIDIn(List<Integer> hotelIDs);
}

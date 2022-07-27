package com.skillstorms.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorms.backend.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
	
	List<Hotel> findByHotelName(String hotelName);

}

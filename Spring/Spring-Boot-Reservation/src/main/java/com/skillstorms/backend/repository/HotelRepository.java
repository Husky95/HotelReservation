package com.skillstorms.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorms.backend.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}

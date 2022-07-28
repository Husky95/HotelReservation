package com.skillstorms.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorms.backend.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}

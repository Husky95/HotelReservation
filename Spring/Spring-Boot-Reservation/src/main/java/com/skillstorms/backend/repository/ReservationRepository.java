package com.skillstorms.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.skillstorms.backend.model.Reservation;

public interface ReservationRepository  extends JpaRepository<Reservation, Integer>{

}

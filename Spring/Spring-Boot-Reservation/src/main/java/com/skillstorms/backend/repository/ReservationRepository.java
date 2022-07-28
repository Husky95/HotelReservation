package com.skillstorms.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorms.backend.model.Reservation;
@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Integer>{

}

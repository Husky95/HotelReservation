package com.skillstorms.backend.service;

import com.skillstorms.backend.model.Reservation;

public interface ReservationService {
	Reservation findVacancy(String Date);

}

package com.skillstorms.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorms.backend.model.DateChecker;
import com.skillstorms.backend.model.Hotel;
import com.skillstorms.backend.model.Reservation;
import com.skillstorms.backend.model.ReservationCount;
import com.skillstorms.backend.repository.HotelRepository;
import com.skillstorms.backend.repository.ReservationRepository;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  private HotelRepository hotelRepository;

  public List < ReservationCount > getReservationCountByHotel(LocalDate arrivalDate, LocalDate departDate) {
    return reservationRepository.countReservationsByHotelForDateRange(arrivalDate, departDate);
  }

  public List < ReservationCount > getReservationCountByHotel(LocalDate arrivalDate, LocalDate departDate, List < Integer > hotelIDs) {
    return reservationRepository.countReservationsByHotelForDateRange(arrivalDate, departDate, hotelIDs);
  }

  public ArrayList < Hotel > getVacantHotel(String startDate, String endDate) {
    Iterable < Reservation > ReservationList = reservationRepository.findAll();
    ArrayList < Integer > hotelList = new ArrayList < Integer > ();
    //int[] hotelMaxRooms = new int[] { 40, 40, 40 };

    //This go through the reservation list and check if there matching date in arrivalDate or departDate
    //if there is add the hotelID to a hotel List 
    //if not return a list of all hotel 
    ReservationList.forEach(
      (element) -> {
        LocalDate arrivalDate = LocalDate.parse(startDate);
        LocalDate departDate = LocalDate.parse(endDate);

        DateChecker checker = new DateChecker(arrivalDate, departDate);
        //int compareValue = arrivalDate.compareTo(element.getArrivalDate());

        if (checker.checkConflictRange(element.getArrivalDate(), element.getDepartDate())) {
          Hotel hotel = element.getHotel();
          hotelList.add(hotel.getHotelID());

        } else {
          //System.out.println("input date is available");
          //ArrayList<Hotel> allHotel = new ArrayList<Hotel>(hotelRepository.findAll());
        }
      }
    );

    //Check if hotelList for matching date > 0 
    //if not return all list 
    //if it is go do the logic
    if (hotelList.size() > 0) {
      Set < Integer > hotelSet = new HashSet < > (hotelList);
      ArrayList < Integer > occurencesList = new ArrayList < Integer > (hotelSet.size());
      //ArrayList<Hotel> vacantHotel = new ArrayList<Hotel>();
      ArrayList < Hotel > allHotel = new ArrayList < Hotel > (hotelRepository.findAll());

      //this go through hotelSet with matching date and check the number of hotelID occurences
      //if the total occurences is less than the hotel max capacity display the hotel 
      //if it not then remove the hotel from the hotelSet 
      for (int i = 0; i < hotelSet.size(); i++) {
        Integer hotelID = hotelSet.iterator().next();
        occurencesList.add(Collections.frequency(hotelList, hotelID));
        //if occurence is less than capacity < remove it from the list 
        //if not add it to the list 
        
        if (occurencesList.get(i) >= hotelRepository.findById(hotelID).get().getTotalRoom()) {
          Optional < Hotel > hotel = hotelRepository.findById(hotelID);
          Hotel noVacant = hotel.isPresent() ? hotel.get() : null;
          allHotel.remove(noVacant);
          hotelSet.remove(hotelID);
        } else {
          continue;
          //Optional <Hotel> hotel = hotelRepository.findById(hotelID);
          //Hotel vacant = hotel.isPresent() ? hotel.get() : null;
          //vacantHotel.add(vacant);
        }

      }
      //System.out.println(allHotel);
      return allHotel;
    } else {
      ArrayList < Hotel > allHotel = new ArrayList < Hotel > (hotelRepository.findAll());
      return allHotel;
    }
  }
  public List<Reservation> findByHotelIDAndDateRagne(int hotelID, LocalDate arrivalDate, LocalDate departDate) {
		return reservationRepository.findByHotelIDAndDateRange(hotelID, arrivalDate, departDate);
	}
	
	/**
	 * Given a list of reservations, determines the maximum amount they overlap at one time.
	 * @param reservations
	 * @return The maximum number of reservations on the same day for the given list
	 */
	public int getMaxReservationOverlap(List<Reservation> reservations) {
		int numReservations = reservations.size();
		LocalDate[] arrivals = new LocalDate[numReservations];
		LocalDate[] departures = new LocalDate[numReservations];
		ListIterator<Reservation> it = reservations.listIterator();
		while (it.hasNext()) {
			int i = it.nextIndex();
			Reservation r = it.next();
			arrivals[i] = r.getArrivalDate();
			departures[i] = r.getDepartDate();
		}
		Arrays.sort(arrivals);
		Arrays.sort(departures);
		int arrivalIndex = 0;
		int departIndex = 0;
		int currentOverlap = 0;
		int maxOverlap = 0;
		while (arrivalIndex < numReservations && departIndex < numReservations) {
			if (arrivals[arrivalIndex].isBefore(departures[departIndex])) {
				currentOverlap++;
				maxOverlap = Math.max(maxOverlap, currentOverlap);
				arrivalIndex++;
			}
			else {
				currentOverlap--;
				departIndex++;
			}
		}
		return maxOverlap;
	}
}
package com.skillstorms.backend.model;

import java.time.LocalDate;

public class DateChecker {
	private LocalDate arrivalDate;
	private LocalDate departDate;
	public DateChecker(LocalDate arrive,LocalDate depart){
		this.arrivalDate = arrive;
		this.departDate = depart;
	}
	public DateChecker(LocalDate arrive){
		this.arrivalDate = arrive;
	}
	
	
	public boolean checkConflictArrival(LocalDate startDate, LocalDate endDate) {
		return !(arrivalDate.isBefore(startDate) || arrivalDate.isAfter(endDate));
	}
	
	public boolean checkConflictRange(LocalDate arrivalDate2, LocalDate departDate2) {
		return ( (this.arrivalDate.isBefore(departDate2)) &&  (arrivalDate2.isBefore(this.departDate)));
	}
	
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public LocalDate getDepartDate() {
		return departDate;
	}
	public void setDepartDate(LocalDate departDate) {
		this.departDate = departDate;
	}
	

}

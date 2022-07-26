package com.skillstorms.backend.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Entity(name="Reservation") // This tells Hibernate to make a table out of this class
@Table(name="Reservation")
public class Reservation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Reservation_Number")
	int reservationNumber;
	
    @Column(name = "Reserve_Date")
    private LocalDate reserveDate;
    
    @Column(name = "Arrival_Date")
    @NotNull
    private LocalDate arrivalDate;
    
    @Column(name = "Depart_Date")
    @NotNull
    private LocalDate departDate;
    
    @Column(name = "Num_Adults")
    @NotNull
    private long numAdults;
    
    @Column(name = "Num_Kids")
    @NotNull
    private long numKids;
    
    @Column(name = "Num_Beds")
    @NotNull
    private long numBeds;

	@Column(name = "Bed_Type")
    @NotBlank
    private String bedType;
    
    @Column(name = "Room_Number")
    @NotNull
    private long roomNumber;    
    

    
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne()
    @JoinColumn(name = "Customer_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIdentityReference(alwaysAsId = true)
    private Customer customer;
    
    
    @ManyToOne
    @JoinColumn(name = "Hotel_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityReference(alwaysAsId = true)
    private Hotel hotel;
    
    
    public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public long getNumBeds() {
		return numBeds;
	}
	public void setNumBeds(long numBeds) {
		this.numBeds = numBeds;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	
	@Override
	public String toString() {
		return "Reservation [reservationNumber=" + reservationNumber + ", reserveDate=" + reserveDate + ", arrivalDate="
				+ arrivalDate + ", departDate=" + departDate + ", numAdults=" + numAdults + ", numKids=" + numKids
				+ ", bedType=" + bedType + ", roomNumber=" + roomNumber +  "]";
	}
	public LocalDate getReserveDate() {
		return reserveDate;
	}
	
	public void setReserveDate( LocalDate reserveDate) {
		this.reserveDate = reserveDate;
	}
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate( LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public LocalDate getDepartDate() {
		return departDate;
	}
	public void setDepartDate( LocalDate departDate) {
		this.departDate = departDate;
	}
	public long getNumAdults() {
		return numAdults;
	}
	public void setNumAdults(long numAdults) {
		this.numAdults = numAdults;
	}
	public long getNumKids() {
		return numKids;
	}
	public void setNumKids(long numKids) {
		this.numKids = numKids;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public long getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(long roomNumber) {
		this.roomNumber = roomNumber;
	}


}

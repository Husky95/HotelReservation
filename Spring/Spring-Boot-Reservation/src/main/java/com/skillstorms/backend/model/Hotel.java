package com.skillstorms.backend.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="Hotel") // This tells Hibernate to make a table out of this class
@Table(name="Hotel")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class ,property = "hotelID")
public class Hotel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "Hotel_ID")
	private int hotelID;
    
	@Column(name = "Hotel_Name")
    @NotBlank
    private String hotelName;
    
	@Column(name = "Street")
    @NotBlank
    private String street;
   
    @Column(name = "City")
    @NotBlank
    private String city;
   
    @Column(name = "State")
    @NotBlank
    private String state;
    
    @Column(name = "Zipcode")
    @NotNull
    private String zipcode;
    
    @Column(name = "Phone")
    @NotNull
    private long phone;
    
    @Column(name = "Total_Rooms")
    @NotNull
    private long totalRooms;
    
    @OneToMany(mappedBy="hotel")
    private Set<Reservation> reservations;

	@Override
	public String toString() {
		return "Hotel [hotelID=" + hotelID + ", hotelName=" + hotelName + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", zipcode=" + zipcode + ", phone=" + phone + ", totalRooms=" + totalRooms + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(hotelID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		return hotelID == other.hotelID;
	}
	public int getHotelID() {
		return hotelID;
	}
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public long getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(long totalRooms) {
		this.totalRooms = totalRooms;
	}
	public Set<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}
}

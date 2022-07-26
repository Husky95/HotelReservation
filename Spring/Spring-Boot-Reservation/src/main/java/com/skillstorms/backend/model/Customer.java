package com.skillstorms.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int customerID;
	String firstName;
	String lastName;
	String street;
	String city;
	String state;
	long zipcode;
	long phone;
	int ReservationID;

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + ", phone="
				+ phone + ", ReservationID=" + ReservationID + "]";
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public long getZipcode() {
		return zipcode;
	}
	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public int getReservationID() {
		return ReservationID;
	}
	public void setReservationID(int reservationID) {
		ReservationID = reservationID;
	}
}

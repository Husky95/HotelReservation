package com.skillstorms.backend.model;

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

@Entity(name="Customer") 
@Table(name="Customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "customerID")
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Customer_ID")
	private int customerID;
	
    @Column(name = "Firstname")
    @NotBlank
    private String firstName;
    
    @Column(name = "Lastname")
    @NotBlank
    private String lastName;
    
    @Column(name = "Email")
    @NotBlank
    private String Email;
    
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
    private long zipcode;
    
    @Column(name = "Phone")
    @NotNull
    private long phone;

    @OneToMany(mappedBy = "customer")
//	@JsonManagedReference // This does get serialized, but it's back half doesn't
	private Set<Reservation> reservations;

    
    
	
	public Set<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + ", phone="
				+ phone + "]";
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
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
}

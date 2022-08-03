package com.skillstorms.backend.model;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Entity(name="Hotel") // This tells Hibernate to make a table out of this class
@Table(name="Hotel")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hotelID")
public class Hotel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class ,property = "hotelID")
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

    @Column(name = "Rating")
    @NotNull
    private float rating;
    
    @Column(name = "Price")
    @NotNull
    private float price;
    
    
    public long getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(long totalRooms) {
		this.totalRooms = totalRooms;
	}
	public @NotNull float getRating() {
		return rating;
	}
	public void setRating(@NotNull float f) {
		this.rating = f;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float f) {
		this.price = f;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	@Column(name = "Descriptions")
    @NotNull
    private String Description;
    
    //@OneToMany(mappedBy = "customer")
    //@JsonManagedReference // This does get serialized, but it's back half doesn't
	//private Set<Reservation> reservations;
   

	public int getHotelID() {
		return hotelID;
	}
	@Override
	public String toString() {
		return "Hotel [hotelID=" + hotelID + ", hotelName=" + hotelName + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", zipcode=" + zipcode + ", phone=" + phone + ", totalRooms=" + totalRooms
				+ ", rating=" + rating + ", price=" + price + ", Description=" + Description + "]";
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
	public long getTotalRoom() {
		return totalRooms;
	}
	public void setTotalRoom(long totalRoom) {
		this.totalRooms = totalRoom;
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
	
	}

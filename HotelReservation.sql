create database HotelReservationDB;
use HotelReservationDB;
SET FOREIGN_KEY_CHECKS=0;

create table Hotel (Hotel_ID int unique not null auto_increment,
						Hotel_Name varchar(40),
                        Street varchar(20),
                        City varchar(20),
						State varchar(20),
                        Zipcode long,
                        Phone long,
						Total_Rooms long,
						primary key(Hotel_ID)

);
create table Customer (Customer_ID int not null auto_increment,
						Firstname varchar(200),	
						Lastname varchar(200),
                        Email varchar(200),
                        Street varchar(100),
						City varchar(20),
						State varchar(20),
                        Zipcode long,
						Phone long,
                        primary key(Customer_ID)
);



create table Reservation (Reservation_Number int not null auto_increment,
						Reserve_Date Date,
                        Arrival_Date Date,
                        Depart_Date Date,
                        Num_Adults long,
                        Num_Kids long,
						Num_Beds int,
                        Bed_Type varchar(20), 
                        Room_Number long, 
                        Customer_ID int ,
                        Hotel_ID int ,
						foreign key (Customer_ID) references Customer(Customer_ID) ON DELETE CASCADE,
						foreign key (Hotel_ID) references Hotel(Hotel_ID),
						primary key(Reservation_Number)

);






insert into Hotel values(1, 'UnitPrime', '9241 13th Ave SW', 'Seattle', 'WA', 98106, 5099557226,40);
insert into Hotel values(2, 'UnitTwo', '9241 13th Ave SW', 'Malvern', 'PA', 898106, 50943557226,40);
insert into Hotel values(3, 'UnitPrime', '9241 13th Ave SW', 'Seattle', 'WA', 98106, 5099557226,40);

insert into Customer values(1, 'John', 'Doe','custer@gmail.com','828 South Galvin Drive', 'Dallas', 'TX', 75213, 2149471611);

insert into Reservation values(1, '2022-07-26', '2022-07-27', '2022-07-28', 2, 0, 2, 'Queen', 24, 1, 1);




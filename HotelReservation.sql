create database HotelReservationDB;
use HotelReservationDB;

create table Hotel (Hotel_ID int not null auto_increment,
						Hotel_Name varchar(20),
                        Street varchar(20),
                        City varchar(20),
						State varchar(20),
                        Zipcode long,
                        Phone long,
						primary key(Hotel_ID)

);

create table Reservation (Reservation_Number int not null auto_increment,
						Reserve_Date varchar(20),
                        Arrival_Date varchar(20),
                        Depart_Date varchar(20),
                        Num_Adults long,
                        Num_Kids long,
						Bed_Type varchar(20),
                        Room_Number long, 
                        Hotel_ID int,
						foreign key (Hotel_ID) references Hotel(Hotel_ID),
						primary key(Reservation_Number)

);

create table Customer  (Customer_ID int not null auto_increment,
						Firstname varchar(200),
						Lastname varchar(200),
                        Street varchar(100),
						City varchar(20),
						State varchar(20),
                        Zipcode long,
						Phone long,
                        Reservation_ID int,
						foreign key (Reservation_ID) references Reservation(Reservation_Number),
                        primary key(Customer_ID)
);






insert into Hotel values(1, 'UnitPrime', '9241 13th Ave SW', 'Seattle', 'WA', 98106, 5099557226);
insert into Reservation values(1, '7/26/2022', '7/28/2022', '7/28/2022', 2, 0, 'Queen', 24, 1);
insert into Customer values(1, 'John', 'Doe', '828 South Galvin Drive', 'Dallas', 'TX', 7523, 2149471611, 1);




create database HotelReservationDB;
use HotelReservationDB;
SET FOREIGN_KEY_CHECKS=0;

create table Hotel (Hotel_ID int unique not null auto_increment,
						Hotel_Name varchar(200),
                        Street varchar(20),
                        City varchar(20),
						State varchar(20),
                        Zipcode long,
                        Phone long,
						Total_Rooms long,
                        Rating float,
                        Price float,
                        Descriptions varchar(500),
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






insert into Customer (Firstname, Lastname, Email, Street, City, State, Zipcode, Phone)
values('John', 'Doe','Doe@gmail.com','828 South Galvin Drive', 'Dallas', 'TX', 75213, 2149471611);
insert into Customer (Firstname, Lastname, Email, Street, City, State, Zipcode, Phone)
values('Bob', 'Smith','Smith@gmail.com', '123 Main St', 'Atlanta', 'GA', 12345, 5556667777);
insert into Customer (Firstname, Lastname, Email, Street, City, State, Zipcode, Phone)
values('John', 'Tyler', 'Tyler@gmail.com', '456 Oak St', 'Miami', 'FL', 67890, 1112223333);

insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-26', '2022-07-27', '2022-07-28', 2, 0, 2, 'Queen', 24, 1, 1);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-01', '2022-08-05', 2, 0, 2, 'Queen', 1, 1, 1);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-01', '2022-08-05', 2, 0, 2, 'Queen', 1, 1, 2);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-10', '2022-08-12', 2, 0, 2, 'Queen', 1, 1, 2);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-13', '2022-08-14', 2, 0, 2, 'Queen', 1, 1, 2);

insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-01', '2022-09-02', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-01', '2022-08-02', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-02', '2022-08-03', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-08-03', '2022-08-04', 2, 0, 2, 'Queen', 1, 1, 6);

insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-01', '2022-09-03', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-02', '2022-09-05', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-04', '2022-09-08', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-05', '2022-09-07', 2, 0, 2, 'Queen', 1, 1, 6);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-06', '2022-09-09', 2, 0, 2, 'Queen', 1, 1, 6);

insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-01', '2022-09-03', 2, 0, 2, 'Queen', 1, 1, 7);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-02', '2022-09-05', 2, 0, 2, 'Queen', 1, 1, 7);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-04', '2022-09-08', 2, 0, 2, 'Queen', 1, 1, 7);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-05', '2022-09-07', 2, 0, 2, 'Queen', 1, 1, 7);
insert into Reservation (Reserve_Date, Arrival_Date, Depart_Date, Num_Adults, Num_Kids, Num_Beds, Bed_Type, Room_Number, Customer_ID, Hotel_Id)
values('2022-07-31', '2022-09-06', '2022-09-09', 2, 0, 2, 'Queen', 1, 1, 7);


insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('UnitPrime', '9241 13th Ave SW', 'Seattle', 'WA', 98106, 5099557226,1, 5, 1000, 
"A very luxurious single suit room using the only the latest technology." );

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Giraffe Manor', '9241 13th Ave SW', 'Malvern', 'PA', 898106, 50943557226,40 , 4.5 , 500, 
"The exclusive boutique hotel Giraffe Manor is located in one of Nairobi’s most iconic buildings, and its timeless elegance recalls the 1930s when European visitors first flocked to East Africa to enjoy safaris."
);

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Buena Vista Suites Orlando', '8203 World Center Dr', 'Orlando', 'FL', '32821', 4072398588, 1, 2, 565 , 
"Founded in 1228 and later purchased by the Guiness family, this palatial castle is brimming with culture and history. The is not only magnificent, but it has a cult following among guests.");

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Handlery Union Square Hotel', '351 Geary St', 'San Francisco', 'CA', '94102', 4157817800, 2, 4, 332, 
"When an architect was faced with a challenge on how to build homes over a pedestrian bridge, this is how he answered the call – with curious and strange cube homes. Not only are the outside of the homes unforgettable, but the inside too. Each room is oddly shaped with angled walls and interesting skylights. ");

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Sonder The Witherspoon', '130 South Juniper St', 'Philadelphia', 'PA', '19107', 6173000956, 3, 3.5, 255, 
"While you are cruising down Route 66 you can pull into this very unique roadside motel, The Sonder. Built in 1949 it’s a nod back to those great American road trip days and makes a fun and quirky place to stay. ");

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Finn Lough Resort', '9241 13th Ave SW', 'Portland', 'OR', '88106', 7099557226, 4, 1.5 , 99, 
"Likely one of the most unique hotel rooms on the planet. These idyllic bubble domes are set back into the green forest of Northern Ireland and offer guests a true escape. The transparent walls give you an uninterrupted 180° view of the trees and the sky, which past guests have said is phenomenal on a clear night. ");

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Wrigley Hostel', '3514 Sheffield Ave', 'Chicago', 'IL', '60657', 1234567890, 2, 3, 199, 
"");

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('IceHotel', '701 Michigan Ave', 'Chicago', 'IL', '60611', 1234567890, 3 , 4, 325,
"The hotel also includes an ice church, ice bar, ice sculpting studio and pillar hall, in addition to two heated restaurants, lounge, four meeting rooms and two wilderness camps." );

insert into Hotel (Hotel_Name, Street, City, State, Zipcode, Phone, Total_Rooms, Rating, Price, Descriptions ) 
values('Homewood Suites', '40 East Grand Ave', 'Chicago', 'IL', '60611', 1234567890, 4, 3.5 , 799, 
" The hotel’s luxurious interior bears the unmistakable stamp of Gehry’s design. Tilted walls, zigzag windows, cathedral-height ceilings and a host of bespoke details create the impression of accommodation as a work of art in its 43 rooms & suites.");


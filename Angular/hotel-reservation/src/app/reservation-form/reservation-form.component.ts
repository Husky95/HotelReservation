import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CustomerApiService } from '../customer-api.service';
import { HotelDataService } from '../services/hotel-data.service';
import { ReservationApiService } from '../services/reservation-api.service';

@Component({
    selector: 'app-reservation-form',
    templateUrl: './reservation-form.component.html',
    styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent implements OnInit {

    @Input() showBack: boolean = true
    @Output() hideForm = new EventEmitter()

    adults: Array<number> = [1, 2, 3, 4]
    children: Array<any> = ["0", 1, 2, 3]
    beds: Array<string> = ["Full", "Queen", "King"]
    numOfAdults: number = 0
    numOfChildren: number = 0

    hotelData: HotelDataService
    @Input() reservation: any = {
        numAdults: 1,
        numBeds: 1,
        bedType: "Full",
    }

    customer: any = {}

    @Input() dates: Array<Date> = []

    modalData: any = {show: false}

    constructor(hotelData: HotelDataService, private customerService: CustomerApiService, private reservationService: ReservationApiService) { 
        this.hotelData = hotelData
    }

    ngOnInit(): void {
        if (this.reservation.customer != null) {
            // Found customer id, display data
            this.customerService.findById(this.reservation.customer).subscribe(resp => this.customer = resp)
        }
        // Check for existing reservation dates
        if (this.reservation.hasOwnProperty("arrivalDate")) {
            this.dates = [this.reservation.arrivalDate, this.reservation.departDate]
        }
    }

    closeModal() {
        this.modalData = {show: false}
    }

    getData() {
        // let test = this.dates[0].toISOString().substring(0, 10)
        // console.log('Date:', test)

        let data = {
            ...this.reservation,
            numKids: +this.reservation.numKids
        }

        if (this.reservation.customer != null) {
            // Update data
            this.modalData = {
                show: true, 
                type: 'update', 
                data: {
                    customer: this.customer,
                    hotelId: this.hotelData.hotelInfo.hotelID,
                    dates: this.dates, 
                    reservation: data
                }
            }
            console.log("we updating in here")
            
        }
        else {
            // Create new reservation
            this.customerService.save(this.customer).subscribe(resp => {
                this.reservationService.save(resp.customerID, this.hotelData.hotelInfo.hotelID, this.dates, data).subscribe(resp => console.log(resp))
            })
        }
        
    }

    cancelReservation() {
        this.modalData = {
            show: true,
            type: 'cancel',
            data: {
                reservationId: this.reservation.reservationNumber,
                customerId: this.reservation.customer
            }
        }
    }
}

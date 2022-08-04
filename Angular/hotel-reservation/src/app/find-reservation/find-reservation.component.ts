import { Component, OnInit } from '@angular/core';
import { CustomerApiService } from '../services/customer-api.service';
import { HotelApiService } from '../services/hotel-api.service';
import { HotelDataService } from '../services/hotel-data.service';
import { ReservationApiService } from '../services/reservation-api.service';

@Component({
    selector: 'app-find-reservation',
    templateUrl: './find-reservation.component.html',
    styleUrls: ['./find-reservation.component.css']
})
export class FindReservationComponent implements OnInit {

    showReservationFrom: boolean = false
    reservation: any = null
    customer: any = null
    id: number = 0
    hotel: any = null
    dates: Array<Date> = []

    constructor(private reservationService: ReservationApiService, private hotelService: HotelApiService, 
        private hotelData: HotelDataService, private customerService: CustomerApiService) { }

    ngOnInit(): void {
    }

    getReservation(id: number) {
        this.reservationService.findById(id).subscribe(resp => {
            this.reservation = resp
            this.dates = [new Date(resp.arrivalDate.replaceAll("-", "/")), new Date(resp.departDate.replaceAll("-", "/"))]
            this.customerService.findById(resp.customer).subscribe(resp => this.customer = resp)
            this.hotelService.findById(this.reservation.hotel.hotelID).subscribe(resp => this.hotelData.hotelInfo = resp)
        })
    }

    toggleForm(state: boolean) {
        this.showReservationFrom = state
    }
}

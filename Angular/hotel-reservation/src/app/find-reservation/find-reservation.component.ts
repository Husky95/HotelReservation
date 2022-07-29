import { Component, OnInit } from '@angular/core';
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
    id: number = 0
    hotel: any = null

    constructor(private reservationService: ReservationApiService, private hotelService: HotelApiService, private hotelData: HotelDataService ) { }

    ngOnInit(): void {
    }

    getReservation(id: number) {
        this.reservationService.findById(id).subscribe(resp => {
            this.reservation = resp
            this.hotelService.findById(this.reservation.hotel.hotelID).subscribe(resp => this.hotelData.hotelInfo = resp)
        })
    }

    toggleForm(state: boolean) {
        this.showReservationFrom = state
    }
}

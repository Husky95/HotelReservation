import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerApiService } from '../services/customer-api.service';
import { HotelApiService } from '../services/hotel-api.service';
import { ReservationApiService } from '../services/reservation-api.service';

@Component({
    selector: 'app-find-reservation',
    templateUrl: './find-reservation.component.html',
    styleUrls: ['./find-reservation.component.css'],
})
export class FindReservationComponent implements OnInit {

    showReservationFrom: boolean = false
    reservation: any = null
    customer: any = null
    @Input() id!: number
    hotel: any = null
    dates: Array<Date> = []
    
    invalid: boolean = false

    constructor(private reservationService: ReservationApiService, private hotelService: HotelApiService, private customerService: CustomerApiService, 
        private router: Router) { 
        }

    ngOnInit(): void {
        // If on the home route, don't grab the reservation yet
        if (this.router.url != '/')
            this.getReservation(this.id)
    }

    getReservation(id: number): void {
        this.reservationService.findById(id).subscribe(resp => {
                if (resp == null)
                    return this.invalid = true
                else {              
                    this.reservation = resp
                    this.dates = [new Date(resp.arrivalDate.replaceAll("-", "/")), new Date(resp.departDate.replaceAll("-", "/"))]
                    this.customerService.findById(resp.customer).subscribe(resp => this.customer = resp)
                    this.hotelService.findById(this.reservation.hotel.hotelID).subscribe(resp => this.hotel = resp)
                    if (this.router.url == '/')
                        this.router.navigate(['reservation'], {queryParams: {id}})
                    return
                }
            })
    }

    toggleForm(state: boolean) {
        this.showReservationFrom = state
    }


}

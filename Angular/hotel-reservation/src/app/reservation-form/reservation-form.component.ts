import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HotelDataService } from '../services/hotel-data.service';

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
        arrivalDate: "",
        departDate: "",
        numAdults: 1,
        numKids: 0,
        numBeds: 1,
        bedType: "Full",
    }

    constructor(hotelData: HotelDataService) { 
        this.hotelData = hotelData
    }

    ngOnInit(): void {

    }

    getData() {
        console.log(this.numOfAdults)
        console.log(+this.numOfChildren)
    }
}

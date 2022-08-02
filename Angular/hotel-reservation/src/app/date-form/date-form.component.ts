import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { HotelApiService } from '../services/hotel-api.service';

@Component({
    selector: 'app-date-form',
    templateUrl: './date-form.component.html',
    styleUrls: ['./date-form.component.css']
})
export class DateFormComponent implements OnInit {

    hideHotelList: boolean = true
    startDate: any = ''
    endDate: any = ''
    date: Array<Date> = []
    showForm: boolean = false

    locations: Array<any> = []
    selected = {city: null, state: null}

    searchEvent: Subject<void> = new Subject<void>()

    constructor(private service: HotelApiService) { }

    ngOnInit(): void {
        this.service.getLocations().subscribe(resp => this.locations = resp)
    }

    showHotels() {
        this.hideHotelList = false
        this.showForm = false
        console.log(this.selected)
        this.searchEvent.next()
    }

    getDates() {
        console.log(this.date)
    }

    toggleDisplay() {
        this.hideHotelList = !this.hideHotelList
        this.showForm = !this.showForm
    }
}

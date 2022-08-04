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
    date: Array<Date> = []
    showForm: boolean = false

    locations: Array<any> = []
    selected = {city: null, state: null}

    searchEvent: Subject<void> = new Subject<void>()

    dataInvalid = {
        location: false,
        dates: false
    }

    constructor(private service: HotelApiService) { }

    ngOnInit(): void {
        this.service.getLocations().subscribe(resp => this.locations = resp)
    }

    showHotels() {

        if (!this.dataInvalid.location && this.selected != null) {
            if (!this.dataInvalid.dates && this.date.length == 2) {
                this.hideHotelList = false
                this.showForm = false
                console.log(this.selected)
                this.searchEvent.next()
            }
        }
    }

    pastDates() {
        let today = new Date()
        if (this.date[0].getTime() == this.date[1].getTime()) 
            this.date = [this.date[0]]
        
        let past = new Date(today.toLocaleDateString())
        past.setDate(past.getDate() - 1)
        console.log(past)
        if (this.date[0] < past)
            this.date = []     
    }
    
    toggleDisplay() {
        this.hideHotelList = !this.hideHotelList
        this.showForm = !this.showForm
    }

    validateData(type: string) {
        switch(type) {
            case 'location':
                this.selected == null ? this.dataInvalid.location = true : this.dataInvalid.location = false
                break
            case 'dates':
                this.date == null || this.date[1] == null ? this.dataInvalid.dates = true : this.dataInvalid.dates = false
                break
        }
    }
}

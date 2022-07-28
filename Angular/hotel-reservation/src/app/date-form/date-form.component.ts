import { Component, OnInit } from '@angular/core';

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

    cities = [{city: "Philly", state: "PA"}, {city: "NYC", state: "NY"}, {city: "Seattle", state: "WA"},]
    selected = {city: null, state: null}

    constructor() { }

    ngOnInit(): void {
    }

    showHotels() {
        this.hideHotelList = false
        this.showForm = false
        console.log(this.selected)
    }

    getDates() {
        console.log(this.date)
    }

    toggleDisplay() {
        this.hideHotelList = !this.hideHotelList
        this.showForm = !this.showForm
    }
}

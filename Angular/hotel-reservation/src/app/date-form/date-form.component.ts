import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelApiService } from '../services/hotel-api.service';

@Component({
    selector: 'app-date-form',
    templateUrl: './date-form.component.html',
    styleUrls: ['./date-form.component.css']
})
export class DateFormComponent implements OnInit {

    showHotelList: boolean = false
    date: Array<Date> = []

    locations: Array<any> = []
    selected = {city: '', state: ''}


    dataInvalid = {
        location: false,
        dates: false
    }

    hotels: Array<any> = []

    constructor(private service: HotelApiService, private router: Router, private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.service.getLocations().subscribe(resp => this.locations = resp)

        // Grab the query parameters. If they exist grab them and set them to the state.
        this.route.queryParams.subscribe(params => {
            if (Object.keys(params).length != 0) {
                this.selected = {city: params['city'], state: params['state']}
                // Dates are dumb. Create a new date using "/" instead of "-"" because it uses UTC instead of local time
                this.date = [
                    new Date(params['arrivalDate'].replaceAll("-", "/")), 
                    new Date(params['departDate'].replaceAll("-", "/"))
                ]
                this.showHotels()
            }
        })
    }

    showHotels() {
        // Validate the user inputs
        if (!this.dataInvalid.location && this.selected != null) {
            if (!this.dataInvalid.dates && this.date.length == 2) {     
                // Set query parameters to the url           
                const params = {
                    city: this.selected.city,
                    state: this.selected.state,
                    arrivalDate: this.date[0].toISOString().substring(0, 10),
                    departDate: this.date[1].toISOString().substring(0, 10)
                }

                this.router.navigate(['hotels'], {queryParams: {...params}, queryParamsHandling: 'merge', replaceUrl:true})
                
                // Check if the query parameters have sort parameters
                this.route.queryParams.subscribe(params => {
                    this.showHotelList = true
                    if (params.hasOwnProperty('sort') && params.hasOwnProperty('asc'))
                        return this.service.searchHotels(this.selected.city, this.selected.state, this.date, params['sort'], params['asc']).subscribe(resp => this.hotels = resp)  
                    return this.service.searchHotels(this.selected.city, this.selected.state, this.date, 'hotelName', true).subscribe(resp => this.hotels = resp)              
                })

            }
        }
    }

    // Check if the user enters a past date
    pastDates() {
        let today = new Date()
        if (this.date[0].getTime() == this.date[1].getTime()) 
            this.date = [this.date[0]]
        
        let past = new Date(today.toLocaleDateString())
        past.setDate(past.getDate() - 1)
        if (this.date[0] < past)
            this.date = []     
    }
    
    toggleDisplay() {
        this.showHotelList = !this.showHotelList
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

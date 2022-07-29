import { Component, OnInit } from '@angular/core';
import { HotelApiService } from '../services/hotel-api.service';

@Component({
    selector: 'app-hotels-page',
    templateUrl: './hotels-page.component.html',
    styleUrls: ['./hotels-page.component.css']
})
export class HotelsPageComponent implements OnInit {

    hotels: Array<any> = []

    constructor(private service: HotelApiService) { }

    ngOnInit(): void {
        this.service.findAll().subscribe(resp => this.hotels = resp)
    }

}

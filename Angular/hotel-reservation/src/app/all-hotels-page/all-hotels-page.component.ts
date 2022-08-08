import { Component, OnInit } from '@angular/core';
import { HotelApiService } from '../services/hotel-api.service';

@Component({
  selector: 'app-all-hotels-page',
  templateUrl: './all-hotels-page.component.html',
  styleUrls: ['./all-hotels-page.component.css']
})
export class AllHotelsPageComponent implements OnInit {

    hotels: Array<any> = []

    constructor(private service: HotelApiService) { }

    ngOnInit(): void {
        this.service.findAll().subscribe(resp => this.hotels = resp)
    }

}

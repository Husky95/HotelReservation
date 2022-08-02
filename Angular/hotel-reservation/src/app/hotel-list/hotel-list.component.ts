import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { HotelApiService } from '../services/hotel-api.service';
import { HotelDataService } from '../services/hotel-data.service';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css']
})
export class HotelListComponent implements OnInit {

    hotels: any = []
    @Input() date: Array<Date> = []
    @Input() location: any = {}
    @Output() hideHotels = new EventEmitter()

    eventSubscription: Subscription = new Subscription;
    @Input() searchEvent!: Observable<void>;

    constructor(private hotelData: HotelDataService, private service: HotelApiService) { 
    }

    ngOnInit(): void {
        // Use start and end date to search for hotels
        this.eventSubscription = this.searchEvent.subscribe(() => {
            this.service.searchHotels(this.location.city, this.location.state, this.date).subscribe(resp => this.hotels = resp)
        })
    }

    searchHotels(): void {
        
    }

    selectHotel(info: any) {
        this.hotelData.hotelInfo = info
        this.hideHotels.emit()
    }
}

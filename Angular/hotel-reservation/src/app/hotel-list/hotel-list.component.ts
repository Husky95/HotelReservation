import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HotelApiService } from '../services/hotel-api.service';
import { HotelDataService } from '../services/hotel-data.service';
import { MapApiService } from '../services/map-api.service';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css']
})
export class HotelListComponent implements OnInit {

    @Input() hotels: any = []
    @Output() hideHotels = new EventEmitter()

    loading: boolean = true
    mapUrl: string = ''

    constructor(private hotelData: HotelDataService, private service: HotelApiService, private mapService: MapApiService, private route: ActivatedRoute) { 

    }

    ngOnInit(): void {

        // this.route.queryParams.subscribe(params => {
        //     this.mapService.getGeocoding(`${params['city']}, ${params['state']}`).subscribe(resp => {
        //         this.loading = true
        //         this.mapUrl = this.mapService.getStaticMap(resp.results[0].lon, resp.results[0].lat, 12)
        //     })
        // })

        console.log("rendered")                     
    }

    ngOnChanges(changes: SimpleChanges) {
        console.log("Changes first");
        console.log(changes);
        this.hotels = changes['hotels'].currentValue.map((elem: any) => {
            if (elem.rating <= 2)
                return elem = {...elem, ratingCatagory: 'Poor'}
            if (elem.rating <= 3)
                return elem = {...elem, ratingCatagory: 'Average'}
            if (elem.rating <= 4)
                return elem = {...elem, ratingCatagory: 'Good'}
            if (elem.rating <= 5)
                return elem = {...elem, ratingCatagory: 'Excellent'}
        })
    }

    loadedImage() {
        this.loading = false
    }

    selectHotel(info: any) {
        this.hotelData.hotelInfo = info
        this.hideHotels.emit()
    }
}

import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelApiService } from '../services/hotel-api.service';
import { MapApiService } from '../services/map-api.service';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css']
})
export class HotelListComponent implements OnInit {

    @Input() hotels: any = []
    selectedHotel: any = {}
    @Output() hideHotels = new EventEmitter()

    loading: boolean = true
    mapUrl: string = ''

    type: Array<any> = [
        {sort: "Name", icon: 'pi pi-sort-alpha-down', value: ["hotelName", true]}, 
        {sort: "Name", icon: 'pi pi-sort-alpha-up', value: ["hotelName", false]},
        {sort: "Price", icon: 'pi pi-sort-numeric-down', value: ["price", true]},
        {sort: "Price", icon: 'pi pi-sort-numeric-up', value: ["price", false]},
        {sort: "Rating", icon: 'pi pi-sort-numeric-down', value: ["rating", true]},
        {sort: "Rating", icon: 'pi pi-sort-numeric-up', value: ["rating", false]}
    ]
    filter: any = {}

    showForm: boolean = false

    filterBy() {
        const params = {
            sort: this.filter.value[0],
            asc: this.filter.value[1]
        }
        this.router.navigate(['hotels'], {queryParams: {...params}, queryParamsHandling: 'merge'})
    }

    constructor(private service: HotelApiService, private mapService: MapApiService, private router: Router,
        private route: ActivatedRoute) { 

    }

    ngOnInit(): void {

        // this.route.queryParams.subscribe(params => {
        //     this.mapService.getGeocoding(`${params['city']}, ${params['state']}`).subscribe(resp => {
        //         this.loading = true
        //         this.mapUrl = this.mapService.getStaticMap(resp.results[0].lon, resp.results[0].lat, 12)
        //     })
        // })               
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['hotels'].currentValue != changes['hotels'].previousValue)
            this.showForm = false
        //console.log(changes);
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
        this.selectedHotel = info
        //this.hideHotels.emit()
        //this.router.navigate([], {queryParams: {hotelId: info.hotelID}, queryParamsHandling: 'merge'})
        this.showForm = true
    }
}

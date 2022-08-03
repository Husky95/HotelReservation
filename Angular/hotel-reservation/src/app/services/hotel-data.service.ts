import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HotelApiService } from './hotel-api.service';

@Injectable({
    providedIn: 'root'
})
export class HotelDataService {

    hotelInfo: any = {}

    constructor(private service: HotelApiService) { }

   
}

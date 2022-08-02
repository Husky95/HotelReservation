import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class HotelApiService {

    http: HttpClient

    constructor(http: HttpClient) { 
        this.http = http
    }

    findAll(): Observable<any> {
        return this.http.get(environment.apiURL + 'hotel/all')
    }

    findById(id: number): Observable<any> {
        return this.http.get(environment.apiURL + `hotel/${id}`)
    }

    searchHotels(city: string, state: string, dates: Array<Date>): Observable<any> {
        let arrivalDate = dates[0].toISOString().substring(0, 10)
        let departDate = dates[1].toISOString().substring(0, 10)
        console.log(arrivalDate, departDate)
        return this.http.get(environment.apiURL + `hotel/search?city=${city}&state=${state}&arrival-date=${arrivalDate}&depart-date=${departDate}`)
    }

    getLocations(): Observable<any> {
        return this.http.get(environment.apiURL + 'hotel/statecity')
    }
}

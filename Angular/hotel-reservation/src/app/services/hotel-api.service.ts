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

    getLocations(): Observable<any> {
        return this.http.get(environment.apiURL + 'hotel/statecity')
    }
}

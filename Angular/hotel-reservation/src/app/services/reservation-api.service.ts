import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ReservationApiService {

    http: HttpClient

    constructor(http: HttpClient) {
        this.http = http
    }

    findById(id: number): Observable<any> {
        return this.http.get(environment.apiURL + `reservation/${id}`)
    }

    save(customerId: number, hotelId: number, dates: Array<Date>, body: any): Observable<any> {
        let today = new Date()
        body = {
            reserveDate: today.toISOString().substring(0, 10),
            arrivalDate: dates[0].toISOString().substring(0, 10),
            departDate: dates[1].toISOString().substring(0, 10),
            ...body
        }
        return this.http.post(environment.apiURL + `reservation/customer/${customerId}/hotel/${hotelId}`, body)
    }

    update(reservationId: number, customerId: number, hotelId: number, dates: Array<Date>, body: any): Observable<any> {
        delete body.reservationNumber
        delete body.hotel
        delete body.customer
        body = {
            arrivalDate: dates[0].toISOString().substring(0, 10),
            departDate: dates[1].toISOString().substring(0, 10),
            ...body
        }
        return this.http.put(environment.apiURL + `reservation/${reservationId}/customer/${customerId}/hotel/${hotelId}`, body)
    }

    delete(id: number): Observable<any> {
        return this.http.delete(environment.apiURL + `reservation/${id}`)
    }
}

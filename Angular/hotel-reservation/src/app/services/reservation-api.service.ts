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
}

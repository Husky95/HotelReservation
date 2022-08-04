import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class CustomerApiService {

    http: HttpClient

    constructor(http: HttpClient) { 
        this.http = http
    }

    findById(id: number): Observable<any> {
        return this.http.get(environment.apiURL + `customer/${id}`)
    }

    save(body: any): Observable<any> {
        return this.http.post(environment.apiURL + "customer", body)
    }

    update(id: number, body: any): Observable<any> {
        return this.http.put(environment.apiURL + `customer/${id}`, body)
    }

    delete(id: number): Observable<any> {
        return this.http.delete(environment.apiURL + `customer/${id}`)
    }
}
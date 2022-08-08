import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class HotelApiService {

    http: HttpClient

    constructor(http: HttpClient, private service: AuthenticationService) { 
        this.http = http
    }

    //findAll(): Observable<any> {
        //return this.http.get(environment.apiURL + 'hotel/all')
    //}

    findAll(): Observable<any> {
        const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(this.service.getConfig().username + ':' + this.service.getConfig().password) });

        let token = this.service.getConfig().token!;
        console.log(token)
        return this.http.get(environment.apiURL + 'hotel/all', {headers,responseType: 'text' as 'json'})
    }
    
    
    findById(id: number): Observable<any> {
        return this.http.get(environment.apiURL + `hotel/${id}`)
    }

    getLocations(): Observable<any> {
        return this.http.get(environment.apiURL + 'hotel/statecity')
    }
}

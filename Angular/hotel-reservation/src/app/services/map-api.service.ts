import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class MapApiService {

    http: HttpClient

    constructor(http: HttpClient) {
        this.http = http
    }

    getGeocoding(location: string): Observable<any> {
        //let location = `${street} ${city} ${state} ${zipcode}`
        return this.http.get(`https://api.geoapify.com/v1/geocode/search?text=${location}&format=json&apiKey=${environment.apiKey}`)
    }

    getStaticMap(lon: number, lat: number, zoom: number): string{
        return `https://maps.geoapify.com/v1/staticmap?style=osm-carto&width=300&height=200&center=lonlat:${lon},${lat}&zoom=${zoom}
            &marker=lonlat:${lon},${lat};color:%23ff0000;size:small&apiKey=${environment.apiKey}`
    }
}

import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-reservation-page',
    templateUrl: './reservation-page.component.html',
    styleUrls: ['./reservation-page.component.css']
})
export class ReservationPageComponent implements OnInit {

    id: number = 0

    constructor(private router: Router, private route: ActivatedRoute, private location: Location) { 
        this.route.queryParams.subscribe(params => {
            this.id = params['id']
            this.location.replaceState('reservation');
        })
    }

    ngOnInit(): void {
        console.log(this.id)
    }

}

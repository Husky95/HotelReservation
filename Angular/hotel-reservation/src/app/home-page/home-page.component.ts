import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

    showReservationFrom: boolean = false

    constructor() { }

    ngOnInit(): void {
    }

    toggleForm(state: boolean) {
        this.showReservationFrom = state
    }
  
}

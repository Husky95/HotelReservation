import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CustomerApiService } from '../customer-api.service';
import { ReservationApiService } from '../services/reservation-api.service';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

    @Input() modalData: any
    @Output() closeModal = new EventEmitter()
    type: string = ''

    constructor(private reservationService: ReservationApiService, private customerService: CustomerApiService) { }

    ngOnInit(): void {
    }

    cancelReservation(data: any) {
        this.reservationService.delete(data.reservationId).subscribe(resp => {
            this.customerService.delete(data.customerId).subscribe()   
            this.closeModal.emit()
        })
    
    }

    updateReservation(data: any) {
        this.customerService.update(data.customer.customerID, data.customer).subscribe(resp => {
            this.reservationService.update(resp.reservations[0].reservationNumber, resp.customerID, 
                data.hotelId, data.dates, data.reservation).subscribe(resp => console.log(resp))
        })
    }

    exit() {
        this.closeModal.emit()
    }
}

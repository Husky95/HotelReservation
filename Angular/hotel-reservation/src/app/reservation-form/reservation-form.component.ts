import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CustomerApiService } from '../customer-api.service';
import { HotelDataService } from '../services/hotel-data.service';
import { ReservationApiService } from '../services/reservation-api.service';
import { ConfirmationService, MessageService } from 'primeng/api';


@Component({
    selector: 'app-reservation-form',
    templateUrl: './reservation-form.component.html',
    styleUrls: ['./reservation-form.component.css'],
    providers: [ConfirmationService,MessageService]
})

export class ReservationFormComponent implements OnInit {

    @Input() showBack: boolean = true
    @Output() hideForm = new EventEmitter()

    adults: Array<number> = [1, 2, 3, 4]
    children: Array<any> = ["0", 1, 2, 3]
    beds: Array<string> = ["Full", "Queen", "King"]
    numOfAdults: number = 0
    numOfChildren: number = 0

    hotelData: HotelDataService
    @Input() reservation: any = {
        numAdults: 1,
        numBeds: 1,
        bedType: "Full",
    }

    customer: any = {}

    @Input() dates: Array<Date> = []

    constructor(hotelData: HotelDataService, private customerService: CustomerApiService, private reservationService: ReservationApiService, 
        private confirmationService: ConfirmationService, private messageService: MessageService) { 
        this.hotelData = hotelData
    }

    ngOnInit(): void {
        if (this.reservation.customer != null) {
            // Found customer id, display data
            this.customerService.findById(this.reservation.customer).subscribe(resp => this.customer = resp)
        }
        // Check for existing reservation dates
        if (this.reservation.hasOwnProperty("arrivalDate")) {
            this.dates = [this.reservation.arrivalDate, this.reservation.departDate]
        }
    }

    cancelReservation() {
        this.confirmationService.confirm({
            header: 'Cancel Reservation',
            message: 'Are you sure that you want to cancel your reservation',
            accept: () => {
                //Actual logic to perform a confirmation
                this.reservationService.delete(this.reservation.reservationNumber).subscribe(resp => {
                    this.customerService.delete(this.reservation.customer).subscribe() 
                    window.location.reload()  
                })
                this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
            }
        });
    }

    updateReservation(data: any) {
        this.confirmationService.confirm({
            header: 'Update Reservation Information',
            message: 'Is the information correct?',
            accept: () => {
                //Actual logic to perform a confirmation
                this.customerService.update(this.customer.customerID, this.customer).subscribe(resp => {
                    this.reservationService.update(resp.reservations[0].reservationNumber, resp.customerID, 
                        this.hotelData.hotelInfo.hotelID, this.dates, data).subscribe(resp => console.log(resp))
                })
                this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
            }
        });
    }

    returnHome() {
        window.location.reload()
    }

    getData() {
        // let test = this.dates[0].toISOString().substring(0, 10)
        // console.log('Date:', test)

        let data = {
            ...this.reservation,
            numKids: +this.reservation.numKids
        }

        if (this.reservation.customer != null) {
            // Update data
            // this.modalData = {
            //     show: true, 
            //     type: 'update', 
            //     data: {
            //         customer: this.customer,
            //         hotelId: this.hotelData.hotelInfo.hotelID,
            //         dates: this.dates, 
            //         reservation: data
            //     }
            // }
            console.log("we updating in here")
            this.updateReservation(data)
        }
        else {
            // Create new reservation
            this.customerService.save(this.customer).subscribe(resp => {
                this.reservationService.save(resp.customerID, this.hotelData.hotelInfo.hotelID, this.dates, data).subscribe(resp => console.log(resp))
            })
        }
        
    }

    // cancelReservation() {
    //     this.modalData = {
    //         show: true,
    //         type: 'cancel',
    //         data: {
    //             reservationId: this.reservation.reservationNumber,
    //             customerId: this.reservation.customer
    //         }
    //     }
    // }
}

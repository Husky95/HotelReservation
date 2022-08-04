import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CustomerApiService } from '../services/customer-api.service';
import { HotelDataService } from '../services/hotel-data.service';
import { ReservationApiService } from '../services/reservation-api.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { MapApiService } from '../services/map-api.service';
import { HotelApiService } from '../services/hotel-api.service';


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

    @Input() customer: any = {
        firstName: '',
        lastName: '',
        street: '',
        city: '',
        state: ''
    }

    @Input() dates: Array<Date> = []

    location: any

    loading: boolean = true
    imageUrl: string = ""

    dataInvalid = {
        dates: false,
        first: false,
        last: false,
        email: false,
        phone: false,
        street: false,
        city: false,
        state: false,
        zipcode: false
    }

    showConfirmation: boolean = false

    displayURL: string = "https://digital.ihg.com/is/image/ihg/even-hotels-alpharetta-6479711701-4x3"
    images: Array<any> = [
        {
            "thumbnailImageSrc": "https://digital.ihg.com/is/image/ihg/even-hotels-alpharetta-6479711701-4x3",
            "alt": "Hotel Image",
        },
        {
            "thumbnailImageSrc": "https://images.rosewoodhotels.com/is/image/rwhg/hi-hgv-26330925-rhgmodelbedroom-1",
            "alt": "Image of Room 1",
        },
        {
            "thumbnailImageSrc": "https://cache.marriott.com/marriottassets/marriott/GNVAC/gnvac-guestroom-0013-hor-clsc.jpg?interpolation=progressive-bilinear&",
            "alt": "Image of Room 2",
        },
        {
            "thumbnailImageSrc": "https://webbox.imgix.net/images/tgvirijswhqazimw/82bf24d5-0e27-48b0-9deb-edbbdf07f056.jpeg?auto=format,compress&fit=crop&crop=entropy",
            "alt": "Image of Room 3",
        }
    ]

    constructor(hotelData: HotelDataService, private customerService: CustomerApiService, private reservationService: ReservationApiService, 
        private confirmationService: ConfirmationService, private messageService: MessageService, private mapService: MapApiService, private hotelService: HotelApiService) { 
            this.hotelData = hotelData
    }

    ngOnInit(): void {
        // if (this.reservation.customer != null) {
        //     // Found customer id, display data
        //     this.customerService.findById(this.reservation.customer).subscribe(resp => this.customer = resp)
        // }
        // Check for existing reservation dates
        if (this.reservation.hasOwnProperty("arrivalDate")) {
            this.dates = [new Date(this.reservation.arrivalDate.replaceAll("-", "/")), new Date(this.reservation.departDate.replaceAll("-", "/"))]
        }

        // if (this.showBack){
        //     this.hotelService.findById(this.hotelData.hotelInfo.hotelID).subscribe(resp => {this.hotelData.hotelInfo = resp
        //         this.getGeoLocation()
        //     })
        // }
        // else {
        //     console.log(this.reservation.hotel.hotelID)
        //     this.hotelService.findById(this.reservation.hotel.hotelID).subscribe(resp => {this.hotelData.hotelInfo = resp
        //         this.getGeoLocation()
        //     })
        // }
            
        //this.getGeoLocation()
    }

    getGeoLocation() {
        this.mapService.getGeocoding(`${this.hotelData.hotelInfo.street} ${this.hotelData.hotelInfo.city} ${this.hotelData.hotelInfo.state} ${this.hotelData.hotelInfo.zipcode}`)
        .subscribe(resp => {
            this.location = {lon: resp.results[0].lon, lat: resp.results[0].lat}
            this.imageUrl = this.mapService.getStaticMap(resp.results[0].lon, resp.results[0].lat, 14)
        })
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

    noAvailability() {
        this.confirmationService.confirm({
            header: 'No Availability',
            message: 'Sorry, there are no available rooms for these dates. Please select a different set of days or try again later.',
            accept: () => {
                this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
            },
            reject: () => {
                window.location.reload()
            }
        });
    }

    returnHome() {
        window.location.reload()
    }

    getData() {
        // let test = this.dates[0].toISOString().substring(0, 10)
        // console.log('Date:', test)
        if (Object.values(this.dataInvalid).some(check => check == true) || Object.values(this.customer).some(check => check == ''))
            return

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
            this.hotelService.checkAvailability(this.hotelData.hotelInfo.hotelID, this.dates).subscribe(resp => {
                if (resp)
                    this.customerService.save(this.customer).subscribe(resp => {
                        this.reservationService.save(resp.customerID, this.hotelData.hotelInfo.hotelID, this.dates, data).subscribe(resp => {
                            this.reservation = resp
                            this.showConfirmation = true
                        })
                    })
                else 
                    this.noAvailability()   
            })

            
        }
        
    }

    home() {
        window.location.reload()
    }

    getFloor(rating: number) {
        return Math.floor(rating)
    }

    loadedImage() {
        console.log("loading")
        this.loading = false
    }

    validateData(type: string) {
        let mailFormat = /[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+/
        let phoneFormat = /^[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/
        let zipFormat = /^[0-9]{5}$/

        switch(type) {
            case 'dates':
                this.dates == null || this.dates[1] == null ? this.dataInvalid.dates = true : this.dataInvalid.dates = false
                break
            case 'first':
                this.customer.firstName == '' ? this.dataInvalid.first = true : this.dataInvalid.first = false
                break
            case 'last':
                this.customer.lastName == '' ? this.dataInvalid.last = true : this.dataInvalid.last = false
                break
            case 'email':
                this.customer.email.match(mailFormat) ? this.dataInvalid.email = false : this.dataInvalid.email = true
                break
            case 'phone':
                this.customer.phone.match(phoneFormat) ? this.dataInvalid.phone = false : this.dataInvalid.phone = true
                break
            case 'street':
                this.customer.street == '' ? this.dataInvalid.street = true : this.dataInvalid.street = false
                break
            case 'city':
                this.customer.city == '' ? this.dataInvalid.city = true : this.dataInvalid.city = false
                break
            case 'state':
                this.customer.state == '' ? this.dataInvalid.state = true : this.dataInvalid.state = false
                break
            case 'zipcode':
                this.customer.zipcode.match(zipFormat) ? this.dataInvalid.zipcode = false : this.dataInvalid.zipcode = true
                break
        }
    }

    pastDates() {
        console.log(this.dates)
        let today = new Date()
        if (this.dates[0].getTime() == this.dates[1].getTime()) 
            this.dates = [this.dates[0]]
        
        let past = new Date(today.toLocaleDateString())
        past.setDate(past.getDate() - 1)
        console.log(past)
        if (this.dates[0] < past)
            this.dates = []     
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

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CustomerApiService } from '../services/customer-api.service';
import { ReservationApiService } from '../services/reservation-api.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { MapApiService } from '../services/map-api.service';
import { HotelApiService } from '../services/hotel-api.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-reservation-form',
    templateUrl: './reservation-form.component.html',
    styleUrls: ['./reservation-form.component.css'],
    providers: [ConfirmationService, MessageService]
})

export class ReservationFormComponent implements OnInit {

    @Input() showBack: boolean = true
    @Output() hideForm = new EventEmitter()

    adults: Array<number> = [1, 2, 3, 4]
    children: Array<any> = ["0", 1, 2, 3]
    beds: Array<string> = ["Full", "Queen", "King"]
    numOfAdults: number = 0
    numOfChildren: number = 0

    @Input() reservation: any = {
        numAdults: 1,
        numBeds: 1,
        bedType: "Full",
    }

    @Input() hotel: any
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

    constructor(private customerService: CustomerApiService, private reservationService: ReservationApiService, private confirmationService: ConfirmationService, 
        private messageService: MessageService, private mapService: MapApiService, private hotelService: HotelApiService, private router: Router,
        private route: ActivatedRoute) {
        }

    ngOnInit(): void {
        // Grab the dates from the URL
        if (this.dates.length == 0) {
            this.route.queryParams.subscribe(params => {
                this.dates = [new Date(params['arrivalDate'].replaceAll("-", "/")), new Date(params['departDate'].replaceAll("-", "/"))]
            })
        }
            
        // Check for existing reservation dates
        // if (this.reservation.hasOwnProperty("arrivalDate")) {
        //     this.dates = [new Date(this.reservation.arrivalDate.replaceAll("-", "/")), new Date(this.reservation.departDate.replaceAll("-", "/"))]
        // }

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
            
        this.getGeoLocation()
    }

    // Using the street, city, and zipcode. Generate a map photo from the map API
    getGeoLocation() {
        this.mapService.getGeocoding(`${this.hotel.street} ${this.hotel.city} ${this.hotel.state} ${this.hotel.zipcode}`)
        .subscribe(resp => {
            this.location = {lon: resp.results[0].lon, lat: resp.results[0].lat}
            this.imageUrl = this.mapService.getStaticMap(resp.results[0].lon, resp.results[0].lat, 14)
        })
    }

    // Cancellation prompt message
    cancelReservation() {
        this.confirmationService.confirm({
            header: 'Cancel Reservation',
            message: 'Are you sure that you want to cancel your reservation',
            accept: () => {
                //Actual logic to perform a confirmation
                this.reservationService.delete(this.reservation.reservationNumber).subscribe(resp => {
                    this.customerService.delete(this.reservation.customer).subscribe() 
                    this.returnHome()
                })
                this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
            }
        });
    }

    // Upadate prompt message
    updateReservation(data: any) {
        this.confirmationService.confirm({
            header: 'Update Reservation Information',
            message: 'Is the information correct?',
            accept: () => {
                //Actual logic to perform a confirmation
                this.customerService.update(this.customer.customerID, this.customer).subscribe(resp => {
                    this.reservationService.update(resp.reservations[0].reservationNumber, resp.customerID, 
                        this.hotel.hotelID, this.dates, data).subscribe()
                })
                this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
            }
        });
    }

    // No avalability prompt message
    noAvailability() {
        this.confirmationService.confirm({
            header: 'No Availability',
            message: 'Sorry, there are no available rooms for these dates. Please select a different set of days, choose a different hotel or try again later.',
            accept: () => {
                window.location.reload()
                window.scrollTo(0, 0)
                this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
            },
            reject: () => {
                this.returnHome()
            }
        });
    }

    returnHome() {
        this.router.navigate(['/'])
        window.scrollTo(0, 0)
    }

    getData() {
        // let test = this.dates[0].toISOString().substring(0, 10)
        // console.log('Date:', test)

        // Validate the user inputs
        if (Object.values(this.dataInvalid).some(check => check == true) || Object.values(this.customer).some(check => check == ''))
            return

        let data = {
            ...this.reservation,
            numKids: +this.reservation.numKids
        }

        // If customer doesn't exist, add a new reservation, else update an existing one
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
            this.updateReservation(data)
        }
        else {
            // Create new reservation
            this.hotelService.checkAvailability(this.hotel.hotelID, this.dates).subscribe(resp => {
                if (resp)
                    this.customerService.save(this.customer).subscribe(resp => {
                        this.reservationService.save(resp.customerID, this.hotel.hotelID, this.dates, data).subscribe(resp => {
                            this.reservation = resp
                            this.showConfirmation = true
                        })
                    })
                else 
                    this.noAvailability()   
            })

            
        }
        
    }

    getFloor(rating: number) {
        return Math.floor(rating)
    }

    loadedImage() {
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
        let today = new Date()
        if (this.dates[0].getTime() == this.dates[1].getTime()) 
            this.dates = [this.dates[0]]
        
        let past = new Date(today.toLocaleDateString())
        past.setDate(past.getDate() - 1)
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

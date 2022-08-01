import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http'

import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { DateFormComponent } from './date-form/date-form.component';
import { HotelListComponent } from './hotel-list/hotel-list.component';
import { ReservationFormComponent } from './reservation-form/reservation-form.component';
import { NavbarComponent } from './navbar/navbar.component';

import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { KeyFilterModule } from 'primeng/keyfilter';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HotelsPageComponent } from './hotels-page/hotels-page.component';
import { FindReservationComponent } from './find-reservation/find-reservation.component';
import { PhoneFormatPipe } from './phone-format.pipe';
import { ModalComponent } from './modal/modal.component'

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    DateFormComponent,
    HotelListComponent,
    ReservationFormComponent,
    NavbarComponent,
    HotelsPageComponent,
    FindReservationComponent,
    PhoneFormatPipe,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    InputTextModule,
    ButtonModule,
    KeyFilterModule,
    DropdownModule,
    CalendarModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

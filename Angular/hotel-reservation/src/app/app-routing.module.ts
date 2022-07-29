import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { HotelsPageComponent } from './hotels-page/hotels-page.component';

const routes: Routes = [
    {
        path: "",
        component: HomePageComponent
    },
    {
        path: "hotels",
        component: HotelsPageComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

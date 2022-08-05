import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllHotelsPageComponent } from './all-hotels-page/all-hotels-page.component';
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
    },
    {
        path: "hotels/all",
        component: AllHotelsPageComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

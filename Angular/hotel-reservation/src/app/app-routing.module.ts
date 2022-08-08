import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { HotelsPageComponent } from './hotels-page/hotels-page.component';
import { LoginComponent } from './login/login.component';
import { HistoryComponent } from './history/history.component';
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
        path: "logins",
        component: LoginComponent
    },
    {
        path: "history",
        component: HistoryComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

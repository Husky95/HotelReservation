import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private service: AuthenticationService,private router:Router) { }
  id : any
  displayElement : boolean = true;
  ngOnInit(): void {
    let boolValue = (this.service.getConfig().login == "true"); //returns true

    this.displayElement = boolValue;
    console.log(this.displayElement);
    //this.id = setInterval(() => {
      //this.callMethod(); 
    //}, 100);
  }
  ngOnDestroy() {
    if (this.id) {
      clearInterval(this.id);
    }
  }
  callMethod(){
    let boolValue = (this.service.getConfig().login == "true"); //returns true

    //console.log(this.service.login);
    //this.displayElement = this.service.login;
    
    console.log(this.service.getConfig())
    this.displayElement = boolValue;

  }

  onLogout(){
    this.service.logout();
    this.router.navigate(["/"]).then(() => {
      window.location.reload();
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username!: string;
  password!: string;
  message: any

  constructor(private service: AuthenticationService,private router:Router, ) { }

  ngOnInit() {
  }

  doLogin() {
    let resp = this.service.signin(this.username, this.password);
    //this.service.user = this.username;
    //this.service.login = true; 
    resp.subscribe(data => {
     this.message = data;
     let token = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');;
     //token = token.split("=")[1];
     
     console.log(token)
     this.service.setConfig(this.username,this.password,"true",token);

     this.router.navigate(["/"]).then(() => {
      window.location.reload();
    });
     
    },
    error => {
      console.log(error)
      console.log("error call")

    }
    );
  }
}
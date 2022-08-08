import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/auth.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})


export class HistoryComponent implements OnInit {

  
  constructor(private service: AuthenticationService) {
   }

  ngOnInit(): void {
    let resp = this.service.history();
    resp.subscribe(data => {
     
     console.log(data);
   
     });
      
   
  

}

}


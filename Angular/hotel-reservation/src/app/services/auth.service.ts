import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  //user : string = "";
  //login: boolean = false;

  constructor(private http:HttpClient) { 
   
  }

signin(username:string,password:string){
  const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
  return this.http.get("http://localhost:8080/login",{headers,responseType: 'text' as 'json'})
}
logout()
{
  localStorage.setItem("user", "");
  localStorage.setItem("login","false");
  this.http.post("http://localhost:8080/logout",{})
}

history()
{
  const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(this.getConfig().username + ':' + this.getConfig().password) });
  return this.http.get("http://localhost:8080/test/"+this.getConfig().username,{headers,responseType: 'text' as 'json'})
}


setConfig(username:string,password:string,login:string,token:string) {
  localStorage.setItem("user", username);
  localStorage.setItem("password", password);

  localStorage.setItem("login",login);
  localStorage.setItem("token",token);

}
getConfig(){
  let username = localStorage.getItem("user")
  let password = localStorage.getItem("password")

  let login = localStorage.getItem("login")
  let token = localStorage.getItem("token")

  return {username,password,login,token}
}
}
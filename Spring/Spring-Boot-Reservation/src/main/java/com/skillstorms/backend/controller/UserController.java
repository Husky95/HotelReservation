package com.skillstorms.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorms.backend.model.Customer;
import com.skillstorms.backend.model.User;
import com.skillstorms.backend.repository.UserRepository;

@RestController // @RestController = @Controller + @ResponseBody
@CrossOrigin("*") // If you don't like CorsFilter, you're in luck. They do the same thing
public class UserController {
	@Autowired
    private UserRepository userRepository;
	@GetMapping("/login")
	public String login(){
		//System.out.println(userAgent);
		return "authenticated successfully" ;
	}
	@GetMapping("/test/{username}")
	public List<User> findUsername(@PathVariable (value = "username") String username){
		return userRepository.findByusername(username);
	
	}
	
	@PostMapping("/test2/register")
	public  User findUsername( @RequestBody User user){
		return userRepository.save(user);
	}

}

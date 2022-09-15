 package com.dev.nou.springjwt.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.nou.springjwt.entity.User;
import com.dev.nou.springjwt.entity.UserRequest;
import com.dev.nou.springjwt.entity.UserResponse;
import com.dev.nou.springjwt.exception.UserNotFoundException;
import com.dev.nou.springjwt.service.IUserService;
import com.dev.nou.springjwt.util.JWTUtil;

@Controller
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private JWTUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(@RequestBody @Valid User user){
		if(userService.findByUsername(user.getUsername()).isPresent()) {
		return ResponseEntity.ok().body( new UserNotFoundException("User with username: "+ user.getUsername()+" Exists"));	
		}
		Integer id=userService.saveUser(user);
		String message= "User with id: "+id+" saved successffully";
		//return new ResponseEntity<String>(message, HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}
	@PostMapping("/loginUser")
	public ResponseEntity<UserResponse> login(@RequestBody @Valid UserRequest userRequest){
		//Validate username/password with DB(required in case of Stateless Authentication)
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		String token=util.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token, "Token generated successfully for user: "+userRequest.getUsername()));
		
	}
	@PostMapping("/getData")
	public ResponseEntity<String> testLoginSuccess(Principal p){
		return ResponseEntity.ok("You are accessing data after login in successfully as "+ p.getName());
	}
	
	
}

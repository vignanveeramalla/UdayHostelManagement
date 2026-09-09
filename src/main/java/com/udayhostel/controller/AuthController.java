package com.udayhostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.udayhostel.dto.LoginDTO;
import com.udayhostel.service.AuthService;
import com.udayhostel.response.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController 
{
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(
	        @Valid @RequestBody LoginDTO dto)
	{
	    String token = authService.loginAdmin(dto.getUsername(),dto.getPassword());

	    ApiResponse<String> response =new ApiResponse<>(200, "Login successful", token);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

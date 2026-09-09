package com.udayhostel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.udayhostel.entity.Admin;
import com.udayhostel.repository.AdminRepository;

@Service
public class AuthService 
{
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	public String loginAdmin(String username, String password) 
	{
		Admin admin=adminRepository.findByEmail(username).orElseThrow(()->new IllegalArgumentException("Invalid username or password"));
		
		if(!passwordEncoder.matches(password, admin.getPassword())) 
		{
			throw new IllegalArgumentException("Invalid username or password");
		}
		return jwtService.generateToken(admin.getEmail());
	}

}

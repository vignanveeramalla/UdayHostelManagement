package com.udayhostel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminDTO 
{
	private int adminId;
	
	@NotBlank(message="Name is required")
	private String name;
	
	@NotBlank(message="Email is required")
	@Email(message="Enter a valid email")
	private String email;
	
	
	@Size(min=6, message="Password must contain at least 6 characters")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String role;

	public AdminDTO() 
	{
		// TODO Auto-generated constructor stub
	}

	public int getAdminId() 
	{
		return adminId;
	}

	public void setAdminId(int adminId) 
	{
		this.adminId = adminId;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public String getRole() 
	{
		return role;
	}

	public void setRole(String role) 
	{
		this.role = role;		
	}

}

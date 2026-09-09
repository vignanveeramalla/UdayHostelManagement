package com.udayhostel.entity;

import jakarta.persistence.*;

@Entity
@Table(name="admin")
public class Admin 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int adminId;
	
	private String name;
	
	@Column(unique=true,nullable=false)
	private String email;
	private String password;
	private String role;
	
	public Admin() 
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

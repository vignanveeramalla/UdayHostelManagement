package com.udayhostel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Facility 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int facilityId;
	
	private String name;
	
	private String description;
	
	private String status;
	
	public Facility() 
	{
		// TODO Auto-generated constructor stub
	}

	public int getFacilityId() 
	{
		return facilityId;
	}

	public void setFacilityId(int facilityId) 
	{
		this.facilityId = facilityId;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}
}

package com.udayhostel.dto;

import jakarta.validation.constraints.NotBlank;

public class FacilityDTO 
{
	private int facilityId;
	
	@NotBlank(message="Facility name is required")
	private String name;
	
	@NotBlank(message="Facility description is required")
	private String description;
	
	@NotBlank(message="Facility status is required")
	private String status;

	public FacilityDTO() 
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

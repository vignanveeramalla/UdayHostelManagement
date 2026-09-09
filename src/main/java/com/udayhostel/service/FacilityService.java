package com.udayhostel.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udayhostel.dto.FacilityDTO;
import com.udayhostel.entity.Facility;
import com.udayhostel.repository.FacilityRepository;
import com.udayhostel.exception.ResourceNotFoundException;

@Service
public class FacilityService 
{
	@Autowired
	private FacilityRepository facilityRepository;
	
	//convert DTO to Entity
	public Facility convertToEntity(FacilityDTO dto) 
	{
		Facility facility=new Facility();
		
		facility.setName(dto.getName());
		facility.setDescription(dto.getDescription());
		facility.setStatus(dto.getStatus());
		
		return facility;
	}
	
	//convert Entity to DTO
	public FacilityDTO convertToDTO(Facility facility) 
	{
		FacilityDTO dto=new FacilityDTO();
		
		dto.setFacilityId(facility.getFacilityId());
		dto.setName(facility.getName());
		dto.setDescription(facility.getDescription());
		dto.setStatus(facility.getStatus());
		
		return dto;
	}
	
	//Save Facility
	public Facility saveFacility(Facility facility) 
	{
		String name=facility.getName().trim();
		
		String status=facility.getStatus().toUpperCase();
		
		//Validate facility name
		if(!name.equalsIgnoreCase("WIFI")&&
		   !name.equalsIgnoreCase("LAUNDRY")&&
		   !name.equalsIgnoreCase("MESS")&&
		   !name.equalsIgnoreCase("CCTV")&&
		   !name.equalsIgnoreCase("MINERAL WATER")&&
		   !name.equalsIgnoreCase("DAILY ROOM CLEANING")) 
		{
			throw new IllegalArgumentException("Invalid facility. Use WIFI, Laundry, Mess, CCTV, Mineral Water or Daily ROOM CLEANING");
		}
		
		//validate facility status
		if(!status.equals("AVAILABLE")&&
		   !status.equals("UNAVAILABLE")) 
		{
			throw new IllegalArgumentException("Invalid facility status. Use AVAILABLE or UNAVAILABLE");
		}
		facility.setName(name);
		facility.setStatus(status);
		
		return facilityRepository.save(facility);
	}
	
	//Get All Facilities
	public List<Facility>getAllFacilities()
	{
		return facilityRepository.findAll();
	}
	
	//Get Facility By ID
	public Facility getFacilityById(int facilityId) 
	{
		return facilityRepository.findById(facilityId).orElseThrow(()->new ResourceNotFoundException("Facility not found with id: "+facilityId));
	}
	
	//Get Facilities By Status
	public List<FacilityDTO>getFacilitiesByStatus(String status)
	{
		List<Facility>facilities=facilityRepository.findByStatusIgnoreCase(status);
		
		return facilities.stream().map(this::convertToDTO).toList();
	}
	
	//Search Facilities By Name
	public List<FacilityDTO>searchFacilitiesByName(String name)
	{
		List<Facility>facilities=facilityRepository.findByNameContainingIgnoreCase(name);
		
		return facilities.stream().map(this::convertToDTO).toList();
	}
	
	//Update Facility
	public Facility updateFacility(int facilityId, FacilityDTO dto) 
	{
		Facility facility=getFacilityById(facilityId);
		
		String name=dto.getName().trim();
		String status=dto.getStatus().toUpperCase();
		
		//Validate facility name
		if(!name.equalsIgnoreCase("WIFI")&&
		   !name.equalsIgnoreCase("LAUNDRY")&&
		   !name.equalsIgnoreCase("MESS")&&
		   !name.equalsIgnoreCase("CCTV")&&
		   !name.equalsIgnoreCase("MINERAL WATER")&&
		   !name.equalsIgnoreCase("DAILY ROOM CLEANING")) 
		{
			throw new IllegalArgumentException("Invalid facility. Use WIFI, Laundry, Mess, CCTV, Mineral Water or Daily Room Cleaning");
		}
		
		//Validate status
		if(!status.equals("AVAILABLE")&&
		   !status.equals("UNAVAILABLE")) 
		{
			throw new IllegalArgumentException("Invalid facility status. Use AVAILABLE or UNAVAILABLE");
		}
		
		facility.setName(name);
		facility.setDescription(dto.getDescription());
		facility.setStatus(status);
		
		return facilityRepository.save(facility);
	}
	
	//Delete Facility
	public String deleteFacility(int facilityId) 
	{
		Facility facility=getFacilityById(facilityId);
		
		facilityRepository.delete(facility);
		
		return "Facility deleted successfully";
	}
	
}

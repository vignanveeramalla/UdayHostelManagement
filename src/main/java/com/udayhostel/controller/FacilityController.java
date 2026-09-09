package com.udayhostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udayhostel.dto.FacilityDTO;
import com.udayhostel.entity.Facility;
import com.udayhostel.service.FacilityService;
import com.udayhostel.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/facilities")
public class FacilityController 
{
	@Autowired
	private FacilityService facilityService;
	
	//Add Facility
	@PostMapping
	public ResponseEntity<ApiResponse<FacilityDTO>>addFacility(@Valid@RequestBody FacilityDTO dto)
	{
		Facility facility=facilityService.convertToEntity(dto);
		
		Facility savedFacility=facilityService.saveFacility(facility);
		
		FacilityDTO responseDTO=facilityService.convertToDTO(savedFacility);
		
		ApiResponse<FacilityDTO>response=new ApiResponse<>(201,"Facility added successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//Get All Facilities
	@GetMapping
	public ResponseEntity<ApiResponse<List<FacilityDTO>>>getAllFacilities()
	{
		List<Facility>facilities=facilityService.getAllFacilities();
		
		List<FacilityDTO>facilityDTOs=facilities.stream().map(facilityService::convertToDTO).toList();
		
		ApiResponse<List<FacilityDTO>>response=new ApiResponse<>(200,"Facilities fetched successfully",facilityDTOs);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Get Facility By ID
	@GetMapping("/id/{facilityId}")
	public ResponseEntity<ApiResponse<FacilityDTO>>getFacilityById(@PathVariable("facilityId")int facilityId)
	{
		Facility facility=facilityService.getFacilityById(facilityId);
		
		FacilityDTO responseDTO=facilityService.convertToDTO(facility);
		
		ApiResponse<FacilityDTO>response=new ApiResponse<>(200,"Facility fetched successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Get Facilities By Status
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<FacilityDTO>>>getFacilitiesByStatus(@PathVariable("status")String status)
	{
		List<FacilityDTO>facilities=facilityService.getFacilitiesByStatus(status);
		
		ApiResponse<List<FacilityDTO>>response=new ApiResponse<>(200,"Facilities fetched successfully",facilities);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Search Facilities By Name
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<FacilityDTO>>>searchFacilitiesByName(@RequestParam String name)
	{
		List<FacilityDTO>facilities=facilityService.searchFacilitiesByName(name);
		
		ApiResponse<List<FacilityDTO>>response=new ApiResponse<>(200,"Facilities searched successfully",facilities);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Update Facility
	@PutMapping("/{facilityId}")
	public ResponseEntity<ApiResponse<FacilityDTO>>updateFacility(@PathVariable("facilityId")int facilityId,@Valid @RequestBody FacilityDTO dto)
	{
		Facility updatedFacility=facilityService.updateFacility(facilityId,dto);
		
		FacilityDTO responseDTO=facilityService.convertToDTO(updatedFacility);
		
		ApiResponse<FacilityDTO>response=new ApiResponse<>(200,"Facility updated successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Delete Facility
	@DeleteMapping("/{facilityId}")
	public ResponseEntity<ApiResponse<String>>deleteFacility(@PathVariable("facilityId")int facilityId)
	{
		String message=facilityService.deleteFacility(facilityId);
		
		ApiResponse<String>response=new ApiResponse<>(200,message,null);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

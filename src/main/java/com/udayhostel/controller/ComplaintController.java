package com.udayhostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.udayhostel.dto.ComplaintDTO;
import com.udayhostel.entity.Complaint;
import com.udayhostel.service.ComplaintService;
import com.udayhostel.response.ApiResponse;

@RestController
@RequestMapping("/complaints")
public class ComplaintController 
{
	@Autowired
	private ComplaintService complaintService;
	
	//Add Complaint
	@PostMapping
	public ResponseEntity<ApiResponse<ComplaintDTO>>addComplaint(@Valid@RequestBody ComplaintDTO dto)
	{
		Complaint complaint=complaintService.convertToEntity(dto);
		
		Complaint savedComplaint=complaintService.saveComplaint(complaint);
		
		ComplaintDTO responseDTO=complaintService.convertToDTO(savedComplaint);
		
		ApiResponse<ComplaintDTO>response=new ApiResponse<>(201,"Complaint created successfully", responseDTO);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	//Get All Complaints
	@GetMapping
	public ResponseEntity<ApiResponse<List<ComplaintDTO>>>getAllComplaints()
	{
		List<Complaint>complaints=complaintService.getAllComplaints();
		
		List<ComplaintDTO>complaintDTOs=complaints.stream().map(complaintService::convertToDTO).toList();
		
		ApiResponse<List<ComplaintDTO>>response=new ApiResponse<>(200,"Complaints fetched successfully",complaintDTOs);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//Get Complaint By ID 
	@GetMapping("/{complaintId}")
	public ResponseEntity<ApiResponse<ComplaintDTO>>getComplaintById(@PathVariable int complaintId)
	{
		Complaint complaint=complaintService.getComplaintById(complaintId);
		
		ComplaintDTO responseDTO=complaintService.convertToDTO(complaint);
		
		ApiResponse<ComplaintDTO>response=new ApiResponse<>(200,"Complaint fetched successfully",responseDTO);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	
	//Get Complaints By Student
	@GetMapping("/student/{studentId}")
	public ResponseEntity<ApiResponse<List<ComplaintDTO>>>getComplaintsByStudentId(@PathVariable("studentId") int studentId)
	{
		List<ComplaintDTO>complaints=complaintService.getComplaintsByStudentId(studentId);
		
		ApiResponse<List<ComplaintDTO>>response=new ApiResponse<>(200,"Student complaints fetched successfully",complaints);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//Get Complaints By Status
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<ComplaintDTO>>>getComplaintsByStatus(@PathVariable String status)
	{
		List<ComplaintDTO>complaints=complaintService.getComplaintsByStatus(status);
		
		ApiResponse<List<ComplaintDTO>>response=new ApiResponse<>(200,"Complaints fetched successfully",complaints);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//Update Complaint Status
	@PutMapping("/{complaintId}/status")
	public ResponseEntity<ApiResponse<ComplaintDTO>>updateComplaintStatus(@PathVariable int complaintId,@RequestParam String status)
	{
		Complaint updatedComplaint=complaintService.updateComplaintStatus(complaintId,status);
		
		ComplaintDTO responseDTO=complaintService.convertToDTO(updatedComplaint);
		
		ApiResponse<ComplaintDTO>response=new ApiResponse<>(200,"Complaint status updated successfully",responseDTO);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//Delete Complaint
	@DeleteMapping("/{complaintId}")
	public ResponseEntity<ApiResponse<String>>deleteComplaint(@PathVariable int complaintId)
	{
		String message=complaintService.deleteComplaint(complaintId);
		
		ApiResponse<String>response=new ApiResponse<>(200,message,null);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}

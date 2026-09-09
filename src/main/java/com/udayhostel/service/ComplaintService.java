package com.udayhostel.service;

import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udayhostel.dto.ComplaintDTO;
import com.udayhostel.entity.Complaint;
import com.udayhostel.repository.ComplaintRepository;
import com.udayhostel.repository.StudentRepository;
import com.udayhostel.exception.ResourceNotFoundException;

@Service
public class ComplaintService 
{
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	//Convert DTO to Entity
	public Complaint convertToEntity(ComplaintDTO dto) 
	{
		Complaint complaint=new Complaint();
		
		complaint.setStudentId(dto.getStudentId());
		complaint.setSubject(dto.getSubject());
		complaint.setDescription(dto.getDescription());
		
		return complaint;
	}
	
	//Convert Entity to DTO
	public ComplaintDTO convertToDTO(Complaint complaint) 
	{
		ComplaintDTO dto=new ComplaintDTO();
		
		dto.setComplaintId(complaint.getComplaintId());
		dto.setStudentId(complaint.getStudentId());
		dto.setSubject(complaint.getSubject());
		dto.setDescription(complaint.getDescription());
		dto.setComplaintDate(complaint.getComplaintDate());
		dto.setStatus(complaint.getStatus());
		
		return dto;
	}
	
	//Save Complaint
	public Complaint saveComplaint(Complaint complaint) {

	    int studentId = complaint.getStudentId();

	    // Check student exists
	    studentRepository.findById(studentId)
	        .orElseThrow(() ->
	            new IllegalArgumentException(
	                "Student not found with id: " + studentId
	            )
	        );

	    // Automatically set complaint date
	    complaint.setComplaintDate(LocalDate.now());

	    // Automatically set initial status
	    complaint.setStatus("OPEN");

	    return complaintRepository.save(complaint);
	}
	
	//Get All Complaints
	public List<Complaint>getAllComplaints()
	{
		return complaintRepository.findAll();
	}
	
	//Get complaint by ID
	public Complaint getComplaintById(int complaintId) 
	{
		return complaintRepository.findById(complaintId).orElseThrow(()->new ResourceNotFoundException("Complaint not found with id: "+complaintId));
	}
	
	//Get complaints by student
	public List<ComplaintDTO>getComplaintsByStudentId(int studentId)
	{
		//Check student exists
		studentRepository.findById(studentId).orElseThrow(()->new IllegalArgumentException("Student not found with id: "+studentId));
		
		List<Complaint>complaints=complaintRepository.findByStudentId(studentId);
		
		return complaints.stream().map(this::convertToDTO).toList();
	}
	
	//Get complaints by status
	public List<ComplaintDTO>getComplaintsByStatus(String status)
	{
		List<Complaint>complaints=complaintRepository.findByStatusIgnoreCase(status);
		
		return complaints.stream().map(this::convertToDTO).toList();
	}
	
	//Update complaint status
	public Complaint updateComplaintStatus(int complaintId, String status) 
	{
		Complaint complaint=getComplaintById(complaintId);
		
		String newStatus=status.toUpperCase();
		
		if(!newStatus.equals("OPEN")&&
		   !newStatus.equals("IN_PROGRESS")&&
		   !newStatus.equals("RESOLVED")) 
		{
			throw new IllegalArgumentException("Invalid complaint status. Use OPEN, IN_PROGRESS or RESOLVED");
		}
		
		complaint.setStatus(newStatus);
		
		return complaintRepository.save(complaint);
	}
	
	//Delete complaint
	public String deleteComplaint(int complaintId) 
	{
		Complaint complaint=getComplaintById(complaintId);
		
		complaintRepository.delete(complaint);
		
		return "Complaint deleted successfully";
	}

}

package com.udayhostel.dto;

import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ComplaintDTO 
{
	private int complaintId;
	
	@Positive(message="Student ID must be greater than 0")
	private int studentId;
	
	@NotBlank(message="Subject is required")
	private String subject;
	
	@NotBlank(message="Description is required")
	private String description;
	
	
	private LocalDate complaintDate;
	
	private String status;
	
	
	public ComplaintDTO() 
	{
		// TODO Auto-generated constructor stub
	}


	public int getComplaintId() 
	{
		return complaintId;
	}


	public void setComplaintId(int complaintId) 
	{
		this.complaintId = complaintId;
	}


	public int getStudentId() 
	{
		return studentId;
	}


	public void setStudentId(int studentId) 
	{
		this.studentId = studentId;
	}


	public String getSubject() 
	{
		return subject;
	}


	public void setSubject(String subject) 
	{
		this.subject = subject;
	}


	public String getDescription() 
	{
		return description;
	}


	public void setDescription(String description) 
	{
		this.description = description;
	}


	public LocalDate getComplaintDate() 
	{
		return complaintDate;
	}


	public void setComplaintDate(LocalDate complaintDate) 
	{
		this.complaintDate = complaintDate;
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

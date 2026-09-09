package com.udayhostel.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Complaint 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int complaintId;
	
	private int studentId;
	
	private String subject;
	
	private String description;
	
	private LocalDate complaintDate;
	
	private String status;
	
	
	public Complaint() 
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

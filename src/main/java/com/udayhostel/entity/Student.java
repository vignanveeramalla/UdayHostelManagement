package com.udayhostel.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name="student")
public class Student 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int studentId;
	
	@NotBlank(message="Name is required")
	private String name;
	
	@NotBlank(message="Mobile number is required")
	@Pattern(
			regexp="^[0-9]{10}$",
			message="Mobile number must contain exactly 10 digits"
			)
	private String mobile;
	
	@NotBlank(message="College is required")
	private String college;
	
	@Positive(message="Room number must be greater than 0")
	private int roomNo;
	
	@NotNull(message="Joining date is required")
	private LocalDate joiningDate;
	
	@Positive(message="Fee must be greater than 0")
	private double fee;
	
	@NotBlank(message="Address is required")
	private String address;
	
	public Student() 
	{
		//
	}
	public int getStudentId() 
	{
		return studentId;
	}
	public void setStudentId(int studentId) 
	{
		this.studentId=studentId;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name=name;
	}
	public String getMobile() 
	{
		return mobile;
	}
	public void setMobile(String mobile) 
	{
		this.mobile=mobile;
	}
	public String getCollege() 
	{
		return college;
	}
	public void setCollege(String  college) 
	{
		this.college=college;
	}
	public int getRoomNo() 
	{
		return roomNo;
	}
	public void setRoomNo(int roomNo) 
	{
		this.roomNo=roomNo;
	}
	public LocalDate getJoiningDate() 
	{
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) 
	{
		this.joiningDate=joiningDate;
	}
	public double getFee() 
	{
		return fee;
	}
	public void setFee(double fee) 
	{
		this.fee=fee;
	}
	public String getAddress() 
	{
		return address;
	}
	public void setAddress(String address) 
	{
		this.address=address;
	}
}
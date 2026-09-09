package com.udayhostel.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

public class PaymentDTO 
{
	private int paymentId;
	
	@Positive(message="Student ID must be greater than 0")
	private int studentId;
	
	@Positive(message="Amount must be greater than 0")
	private double amount;
	
	@NotNull(message="Payment date is required")
	private LocalDate paymentDate;
	
	@NotBlank(message="Payment method is required")
	private String paymentMethod;
	
	@NotBlank(message="Payment status is required")
	private String status;
	
	
	public PaymentDTO() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public int getPaymentId() 
	{
		return paymentId;
	}

	public void setPaymentId(int paymentId) 
	{
		this.paymentId=paymentId;
	}
	
	public int getStudentId() 
	{
		return studentId;
	}
	
	public void setStudentId(int studentId) 
	{
		this.studentId=studentId;
	}
	
	public double getAmount() 
	{
		return amount;
	}
	
	public void setAmount(double amount) 
	{
		this.amount=amount;
	}
	
	public LocalDate getPaymentDate() 
	{
		return paymentDate;
	}
	
	public void setPaymentDate(LocalDate paymentDate) 
	{
		this.paymentDate=paymentDate;
	}
	
	public String getPaymentMethod() 
	{
		return paymentMethod;
	}
	
	public void setPaymentMethod(String paymentMethod) 
	{
		this.paymentMethod=paymentMethod;
	}
	
	public String getStatus() 
	{
		return status;
	}
	
	public void setStatus(String status) 
	{
		this.status=status;
	}
}

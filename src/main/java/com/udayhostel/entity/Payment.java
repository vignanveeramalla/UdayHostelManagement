package com.udayhostel.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int paymentId;
	
	private int studentId;
	
	private double amount;
	
	private LocalDate paymentDate;
	
	private String paymentMethod;
	
	private String status;
	
	public Payment() 
	{
	
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

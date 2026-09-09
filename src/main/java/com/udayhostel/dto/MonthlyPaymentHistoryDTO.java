package com.udayhostel.dto;

import java.time.LocalDate;

public class MonthlyPaymentHistoryDTO 
{
	private int paymentId;
	private int studentId;
	private String studentName;
	private double amount;
	private LocalDate paymentDate;
	private String paymentMethod;
	private String status;
	
	public MonthlyPaymentHistoryDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public MonthlyPaymentHistoryDTO(int paymentId,int studentId,String studentName,
									double amount,LocalDate paymentDate,String paymentMethod,
									String status)
	{
		this.paymentId=paymentId;
		this.studentId=studentId;
		this.studentName=studentName;
		this.amount=amount;
		this.paymentDate=paymentDate;
		this.paymentMethod=paymentMethod;
		this.status=status;
	}

	public int getPaymentId() 
	{
		return paymentId;
	}

	public void setPaymentId(int paymentId) 
	{
		this.paymentId = paymentId;
	}

	public int getStudentId() 
	{
		return studentId;
	}

	public void setStudentId(int studentId) 
	{
		this.studentId = studentId;
	}

	public String getStudentName() 
	{
		return studentName;
	}

	public void setStudentName(String studentName) 
	{
		this.studentName = studentName;
	}

	public double getAmount() 
	{
		return amount;
	}

	public void setAmount(double amount) 
	{
		this.amount = amount;
	}

	public LocalDate getPaymentDate() 
	{
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) 
	{
		this.paymentDate = paymentDate;
	}

	public String getPaymentMethod() 
	{
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) 
	{
		this.paymentMethod = paymentMethod;
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

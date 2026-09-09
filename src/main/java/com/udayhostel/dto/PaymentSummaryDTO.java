package com.udayhostel.dto;

public class PaymentSummaryDTO 
{
	private int studentId;
	private double totalFee;
	private double totalPaid;
	private double balance;
	private String paymentStatus;
	
	public PaymentSummaryDTO() 
	{
		// TODO Auto-generated constructor stub
	}
 
	public PaymentSummaryDTO(int studentId, double totalFee, double totalPaid, double balance, String paymentStatus) 
	{
		this.studentId=studentId;
		this.totalFee=totalFee;
		this.totalPaid=totalPaid;
		this.balance=balance;
		this.paymentStatus=paymentStatus;
	}

	public int getStudentId() 
	{
		return studentId;
	}

	public void setStudentId(int studentId) 
	{
		this.studentId = studentId;
	}

	public double getTotalFee() 
	{
		return totalFee;
	}

	public void setTotalFee(double totalFee) 
	{
		this.totalFee = totalFee;
	}

	public double getTotalPaid() 
	{
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) 
	{
		this.totalPaid = totalPaid;
	}

	public double getBalance() 
	{
		return balance;
	}

	public void setBalance(double balance) 
	{
		this.balance = balance;
	}

	public String getPaymentStatus() 
	{
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) 
	{
		this.paymentStatus = paymentStatus;
	}
	
}

package com.udayhostel.dto;

public class MonthlyPaymentSummaryDTO 
{
	private int year;
	private int month;
	
	private int totalStudents;
	private int paidStudents;
	private int partialStudents;
	private int unpaidStudents;
	
	private double totalExpectedFee;
	private double totalCollected;
	private double totalPending;

	public MonthlyPaymentSummaryDTO() 
	{
		
	}
	
	public MonthlyPaymentSummaryDTO(int year,int month,int totalStudents,int paidStudents,
									int partialStudents, int unpaidStudents,double totalExpectedFee,double totalCollected,double totalPending) 
	{
		this.year=year;
		this.month=month;
		this.totalStudents=totalStudents;
		this.paidStudents=paidStudents;
		this.partialStudents=partialStudents;
		this.unpaidStudents=unpaidStudents;
		this.totalExpectedFee=totalExpectedFee;
		this.totalCollected=totalCollected;
		this.totalPending=totalPending;		
	}

	public int getYear() 
	{
		return year;
	}

	public void setYear(int year) 
	{
		this.year = year;
	}

	public int getMonth() 
	{
		return month;
	}

	public void setMonth(int month) 
	{
		this.month = month;
	}

	public int getTotalStudents() 
	{
		return totalStudents;
	}

	public void setTotalStudents(int totalStudents) 
	{
		this.totalStudents = totalStudents;
	}

	public int getPaidStudents() 
	{
		return paidStudents;
	}

	public void setPaidStudents(int paidStudents) 
	{
		this.paidStudents = paidStudents;
	}

	public int getPartialStudents() 
	{
		return partialStudents;
	}

	public void setPartialStudents(int partialStudents) 
	{
		this.partialStudents = partialStudents;
	}

	public int getUnpaidStudents() 
	{
		return unpaidStudents;
	}

	public void setUnpaidStudents(int unpaidStudents) 
	{
		this.unpaidStudents = unpaidStudents;
	}

	public double getTotalExpectedFee() {
		return totalExpectedFee;
	}

	public void setTotalExpectedFee(double totalExpectedFee) {
		this.totalExpectedFee = totalExpectedFee;
	}

	public double getTotalCollected() 
	{
		return totalCollected;
	}

	public void setTotalCollected(double totalCollected) 
	{
		this.totalCollected = totalCollected;
	}

	public double getTotalPending() 
	{
		return totalPending;
	}

	public void setTotalPending(double totalPending) 
	{
		this.totalPending = totalPending;
	}
}

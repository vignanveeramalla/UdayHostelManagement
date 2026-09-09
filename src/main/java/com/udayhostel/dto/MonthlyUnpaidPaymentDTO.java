package com.udayhostel.dto;

public class MonthlyUnpaidPaymentDTO 
{
	private int studentId;
	private String name;
	private int roomNo;
	private double fee;
	private double totalPaid;
	private double balance;
	private String status;
	
	public MonthlyUnpaidPaymentDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public MonthlyUnpaidPaymentDTO(int studentId,String name,
									int roomNo, double fee,
									double totalPaid,double balance,
									String status) 
	{
		this.studentId=studentId;
		this.name=name;
		this.roomNo=roomNo;
		this.fee=fee;
		this.totalPaid=totalPaid;
		this.balance=balance;
		this.status=status;
	}

	public int getStudentId() 
	{
		return studentId;
	}

	public void setStudentId(int studentId) 
	{
		this.studentId = studentId;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getRoomNo() 
	{
		return roomNo;
	}

	public void setRoomNo(int roomNo) 
	{
		this.roomNo = roomNo;
	}

	public double getFee() 
	{
		return fee;
	}

	public void setFee(double fee) 
	{
		this.fee = fee;
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

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}
}
package com.udayhostel.dto;

public class DashboardDTO 
{
	private long totalStudents;
	private long totalRooms;
	private long totalOccupiedRooms;
	private long totalAvailableRooms;
	private double totalFees;
	private double totalPaid;
	private double totalPending;
	private long totalComplaints;
	private long openComplaints;
	private long pendingEnquiries;

	public DashboardDTO() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public DashboardDTO(long totalStudents,long totalRooms,long totalOccupiedRooms,long totalAvailableRooms,double totalFees,double totalPaid,double totalPending,long totalComplaints,long openComplaints,long pendingEnquiries) 
	{
		this.totalStudents=totalStudents;
		this.totalRooms=totalRooms;
		this.totalOccupiedRooms=totalOccupiedRooms;
		this.totalAvailableRooms=totalAvailableRooms;
		this.totalComplaints=totalComplaints;
		this.openComplaints=openComplaints;
		this.totalFees=totalFees;
		this.totalPaid=totalPaid;
		this.totalPending=totalPending;
		this.pendingEnquiries=pendingEnquiries;
	}

	public long getTotalStudents() 
	{
		return totalStudents;
	}

	public void setTotalStudents(long totalStudents) 
	{
		this.totalStudents = totalStudents;
	}

	public long getTotalRooms() 
	{
		return totalRooms;
	}

	public void setTotalRooms(long totalRooms) 
	{
		this.totalRooms = totalRooms;
	}

	public long getTotalOccupiedRooms() {
		return totalOccupiedRooms;
	}

	public void setTotalOccupiedRooms(long totalOccupiedRooms) {
		this.totalOccupiedRooms = totalOccupiedRooms;
	}

	public long getTotalAvailableRooms() {
		return totalAvailableRooms;
	}

	public void setTotalAvailableRooms(long totalAvailableRooms) {
		this.totalAvailableRooms = totalAvailableRooms;
	}
	
	public double getTotalFees() 
	{
		return totalFees;
	}

	public void setTotalFees(double totalFees) 
	{
		this.totalFees = totalFees;
	}

	public double getTotalPaid() 
	{
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) 
	{
		this.totalPaid = totalPaid;
	}

	public double getTotalPending() {
		return totalPending;
	}

	public void setTotalPending(double totalPending) {
		this.totalPending = totalPending;
	}
	public long getTotalComplaints() 
	{
		return totalComplaints;
	}

	public void setTotalComplaints(long totalComplaints) 
	{
		this.totalComplaints = totalComplaints;
	}

	public long getOpenComplaints() 
	{
		return openComplaints;
	}

	public void setOpenComplaints(long openComplaints) 
	{
		this.openComplaints = openComplaints;
	}

	public long getPendingEnquiries() 
	{
		return pendingEnquiries;
	}

	public void setPendingEnquiries(long pendingEnquiries) 
	{
		this.pendingEnquiries = pendingEnquiries;
	}

	
}

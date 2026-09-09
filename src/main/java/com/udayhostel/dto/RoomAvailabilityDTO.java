package com.udayhostel.dto;

public class RoomAvailabilityDTO 
{

	private int roomNo;
	private int capacity;
	private long occupied;
	private long available;
	private boolean full;
	
	public RoomAvailabilityDTO() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public RoomAvailabilityDTO(int roomNo,int capacity,long occupied,long available,boolean full) 
	{
		this.roomNo=roomNo;
		this.capacity=capacity;
		this.occupied=occupied;
		this.available=available;
		this.full=full;
	}
	
	public int getRoomNo() 
	{
		return roomNo;
	}
	
	public void setRoomNo(int roomNo) 
	{
		this.roomNo=roomNo;
	}
	
	public int getCapacity() 
	{
		return capacity;
	}
	
	public void setCapacity(int capacity) 
	{
		this.capacity=capacity;
	}
	
	public long getOccupied() 
	{
		return occupied;
	}
	
	public void setOccupied(long occupied) 
	{
		this.occupied=occupied;
	}
	
	public long getAvailable() 
	{
		return available;
	}
	
	public void setAvailable(long available) 
	{
		this.available=available;
	}
	
	public boolean isFull() 
	{
		return full;
	}
	
	public void setFull(boolean full) 
	{
		this.full=full;
	}
}

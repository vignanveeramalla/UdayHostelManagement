package com.udayhostel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Room 
{
	@Id
	private int roomNo;
	
	private int capacity;
	
	private int floor;

	public Room() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public Room(int roomNo, int capacity, int floor) 
	{
		this.roomNo=roomNo;
		this.capacity=capacity;
		this.floor=floor;
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
	
	public int getFloor() 
	{
		return floor;
	}
	
	public void setFloor(int floor) 
	{
		this.floor=floor;
	}
}

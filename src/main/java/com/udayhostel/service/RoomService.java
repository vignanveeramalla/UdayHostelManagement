package com.udayhostel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udayhostel.dto.RoomDTO;
import com.udayhostel.entity.Room;
import com.udayhostel.repository.RoomRepository;
import com.udayhostel.repository.StudentRepository;
import com.udayhostel.dto.RoomAvailabilityDTO;
import com.udayhostel.exception.RoomOccupiedException;
import com.udayhostel.exception.ResourceNotFoundException;

@Service
public class RoomService 
{
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	public Room convertToEntity(RoomDTO dto) 
	{
		Room room=new Room();
		
		room.setRoomNo(dto.getRoomNo());
		room.setCapacity(dto.getCapacity());
		room.setFloor(dto.getFloor());
	
		return room;
	}
	
	public RoomDTO convertToDTO(Room room) 
	{
		RoomDTO dto=new RoomDTO();
		
		dto.setRoomNo(room.getRoomNo());
		dto.setCapacity(room.getCapacity());
		dto.setFloor(room.getFloor());
		
		return dto;
	}
	
	public Room saveRoom(Room room)
	{
		int roomNo=room.getRoomNo();
		int capacity=room.getCapacity();
		
		if(roomNo!=101 && roomNo!=102 &&
			roomNo!=103 && roomNo!=104 &&
			roomNo!=201 && roomNo!=202 &&
			roomNo!=203 && roomNo!=204 &&
			roomNo!=301 && roomNo!=302 &&
			roomNo!=303 && roomNo!=304 &&
			roomNo!=401) 
		{
			throw new IllegalArgumentException("Invalid room number. Available rooms are 101-104, 201-204, 301-304 and 401");
		}
		
		if(capacity!=7) 
		{
			throw new IllegalArgumentException("Room capacity must be exactly 7");
		}
		
		if(roomRepository.existsById(roomNo)) 
		{
			throw new IllegalArgumentException("Room already exists with room number: "+roomNo);
		}
			
		return roomRepository.save(room);
	}
	
	public List<Room>getAllRooms()
	{
		return roomRepository.findAll();
	}
	
	public Room getRoomById(int roomNo) 
	{
		return roomRepository.findById(roomNo).orElseThrow(() -> new ResourceNotFoundException("Room not found with room number: " + roomNo));
	}
	
	public RoomAvailabilityDTO getRoomAvailability(int roomNo) 
	{
		Room room=roomRepository.findById(roomNo).orElseThrow(()->new ResourceNotFoundException("Room not found with room number: "+roomNo));
		
		int capacity=room.getCapacity();
		
		long occupied=studentRepository.countByRoomNo(roomNo);
		
		long available=capacity-occupied;
		
		boolean full=occupied>=capacity;
		
		return new RoomAvailabilityDTO(roomNo, capacity, occupied, available, full);
	}
	
	public Room updateRoom(int roomNo, Room room) 
	{
		Room existingRoom = roomRepository.findById(roomNo).orElseThrow(() -> new ResourceNotFoundException("Room not found with room number: " + roomNo));
		
		if(room.getCapacity()!=7) 
		{
			throw new IllegalArgumentException("Room capacity must be exactly 7");
		}
		
		existingRoom.setCapacity(room.getCapacity());
		existingRoom.setFloor(room.getFloor());
		
		return roomRepository.save(existingRoom);
	}
	
	public String deleteRoom(int roomNo) 
	{
		Room room=roomRepository.findById(roomNo).orElseThrow(()->new ResourceNotFoundException("Room not found with room number: "+roomNo));
		
		long occupied=studentRepository.countByRoomNo(roomNo);
		
		if(occupied>0) 
		{
			throw new RoomOccupiedException("Room "+roomNo+" cannot be deleted because "+occupied+" student(s) are currently assigned to it.");
		}
		
		roomRepository.delete(room);
		
		return "Room deleted successfully";
	}
}

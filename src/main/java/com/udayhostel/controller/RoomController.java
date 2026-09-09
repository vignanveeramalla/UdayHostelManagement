package com.udayhostel.controller;

import com.udayhostel.dto.RoomAvailabilityDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.udayhostel.dto.RoomDTO;
import com.udayhostel.entity.Room;
import com.udayhostel.service.RoomService;
import com.udayhostel.response.ApiResponse;

@RestController
@RequestMapping("/rooms")
public class RoomController 
{
	@Autowired
	private RoomService roomService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<RoomDTO>>addRoom(@Valid@RequestBody RoomDTO dto)
	{
		Room room=roomService.convertToEntity(dto);
		
		Room savedRoom=roomService.saveRoom(room);
		
		RoomDTO responseDTO =roomService.convertToDTO(savedRoom);
		
		ApiResponse<RoomDTO> response=new ApiResponse<>(201,"Room created successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping 
	public ResponseEntity<ApiResponse<List<RoomDTO>>>getAllRooms()
	{
		List<Room> rooms=roomService.getAllRooms();
		
		List<RoomDTO> roomDTOs=rooms.stream()
				.map(roomService::convertToDTO)
				.toList();
		
		ApiResponse<List<RoomDTO>>response=new ApiResponse<>(200,"Rooms fetched successfully",roomDTOs);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{roomNo}/availability")
	public ResponseEntity<ApiResponse<RoomAvailabilityDTO>> getRoomAvailability(@PathVariable int roomNo)
	{
		RoomAvailabilityDTO availability=roomService.getRoomAvailability(roomNo);
		
		ApiResponse<RoomAvailabilityDTO> response=new ApiResponse<>(200,"Room availability checked successfully",availability);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{roomNo}")
	public ResponseEntity<ApiResponse<RoomDTO>>getRoomById(@PathVariable int roomNo)
	{
		Room room=roomService.getRoomById(roomNo);
		
		RoomDTO dto=roomService.convertToDTO(room);
		
		ApiResponse<RoomDTO> response=new ApiResponse<>(200,"Room fetched successfully",dto);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{roomNo}")
	public ResponseEntity<ApiResponse<RoomDTO>>updateRoom(@PathVariable int roomNo,@Valid @RequestBody RoomDTO dto)
	{
		Room room=roomService.convertToEntity(dto);
		
		Room updatedRoom=roomService.updateRoom(roomNo, room);
		
		RoomDTO responseDTO=roomService.convertToDTO(updatedRoom);
		
		ApiResponse<RoomDTO>response=new ApiResponse<>(200,"Room updated successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{roomNo}")
	public ResponseEntity<ApiResponse<String>> deleteRoom(@PathVariable int roomNo)
	{
		String message=roomService.deleteRoom(roomNo);
		
		ApiResponse<String> response=new ApiResponse<>(200,message,null);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

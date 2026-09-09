package com.udayhostel.exception;

import java.util.HashMap; 

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;


import com.udayhostel.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>>handleValidationErrors(MethodArgumentNotValidException exception) 
	{
		Map<String, String>errors=new HashMap<>();
		
		exception.getBindingResult().getFieldErrors().forEach(error->
		{
			errors.put(error.getField(), error.getDefaultMessage());
			
		});
		
		ApiResponse<Map<String, String>>response=new ApiResponse<>(400, "Validation failed",errors);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoomFullException.class)
	public ResponseEntity<ApiResponse<String>> handleRoomFull(RoomFullException exception)
	{
		ApiResponse<String> response=new ApiResponse<>(400,exception.getMessage(),null);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoomOccupiedException.class)
	public ResponseEntity<ApiResponse<String>>handleRoomOccupied(RoomOccupiedException ex)
	{
		ApiResponse<String>response=new ApiResponse<>(400,ex.getMessage(),null);
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<String>>handleIllegalArgumentException(IllegalArgumentException exception)
	{
		ApiResponse<String>response=new ApiResponse<>(400,exception.getMessage(),null);
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<String>>handleResourceNotFound(ResourceNotFoundException exception)
	{
		ApiResponse<String>response=new ApiResponse<>(404,exception.getMessage(),null);
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
		
}

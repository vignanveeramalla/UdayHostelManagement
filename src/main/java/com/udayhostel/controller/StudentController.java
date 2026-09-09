package com.udayhostel.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

import java.util.List;

import com.udayhostel.entity.Student;
import com.udayhostel.service.StudentService;
import com.udayhostel.dto.StudentDTO;
import com.udayhostel.response.PageResponse;
import com.udayhostel.response.ApiResponse;

@RestController
@RequestMapping("/students")
public class StudentController 
{
	@Autowired
	private StudentService studentService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<StudentDTO>>addStudent(@Valid @RequestBody StudentDTO dto)
	{
		Student student=studentService.convertToEntity(dto);
		
		Student savedStudent=studentService.saveStudent(student);
		
		StudentDTO responseDTO=studentService.convertToDTO(savedStudent);
		
		ApiResponse<StudentDTO>response=new ApiResponse<>(201,"Student created successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<StudentDTO>>>getAllStudents()
	{		
		List<Student> students=studentService.getAllStudents();
		
		List<StudentDTO> studentDTOs=students.stream()
				.map(studentService::convertToDTO)
				.toList();
		ApiResponse<List<StudentDTO>>response=new ApiResponse<>(200,"Students fetched successfully",studentDTOs);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<StudentDTO>>getStudentById(@PathVariable int id)
	{
		Student student=studentService.getStudentById(id);
		
		StudentDTO dto=studentService.convertToDTO(student);
		
		ApiResponse<StudentDTO> response=new ApiResponse<>(200,"Student fetched successfully",dto);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<StudentDTO>>updateStudent(@PathVariable int id,@Valid@RequestBody StudentDTO dto)
	{
		Student student=studentService.convertToEntity(dto);
		
		Student updatedStudent=studentService.updateStudent(id, student);
		
		StudentDTO responseDTO=studentService.convertToDTO(updatedStudent);
		
		ApiResponse<StudentDTO> response=new ApiResponse<>(200,"Students updated successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>>deleteStudent(@PathVariable int id)
	{
		String message=studentService.deleteStudent(id);
		
		ApiResponse<String> response=new ApiResponse<>(200, message, null);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<StudentDTO>>>searchStudentsByName(@RequestParam String name)
	{
		List<StudentDTO> students=studentService.searchStudentsByName(name);
		
		ApiResponse<List<StudentDTO>> response=new ApiResponse<>(200,"Students fetched successfully",students);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/room/{roomNo}")
	public ResponseEntity<ApiResponse<List<StudentDTO>>>getStudentsByRoomNo(@PathVariable int roomNo)
	{
		List<StudentDTO>students=studentService.getStudentsByRoomNo(roomNo);
		
		ApiResponse<List<StudentDTO>>response=new ApiResponse<>(200,"Students fetched successfully",students);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/college/{college}")
	public ResponseEntity<ApiResponse<List<StudentDTO>>>getStudentsByCollege(@PathVariable String college)
	{
		List<StudentDTO> students=studentService.getStudentsByCollege(college);
		
		ApiResponse<List<StudentDTO>> response=new ApiResponse<>(200,"Students fetched successfully",students);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<ApiResponse<List<StudentDTO>>>filterStudents(@RequestParam String college, @RequestParam int roomNo)
	{
		List<StudentDTO> students=studentService.getStudentsByCollegeAndRoom(college, roomNo);
		
		ApiResponse<List<StudentDTO>> response=new ApiResponse<>(200,"Students fetched successfully",students);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<PageResponse<StudentDTO>>getStudentsWithPagination(Pageable pageable)
	{
		Page<StudentDTO>students=studentService.getStudentsWithPagination(pageable);
		
		PageResponse<StudentDTO>response=new PageResponse<>(
				students.getContent(),
				students.getNumber(),
				students.getSize(),
				students.getTotalElements(),
				students.getTotalPages(),
				students.isFirst(),
				students.isLast()
				);
				
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/fee")
	public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsByFee(@RequestParam double fee)
	{
		List<StudentDTO>students=studentService.getStudentsByFee(fee);
		
		ApiResponse<List<StudentDTO>> response=new ApiResponse<>(200,"Students fetched successfully",students);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/fee-range")
	public ResponseEntity<ApiResponse<List<StudentDTO>>>getStudentsByFeeRange(@RequestParam double minFee, @RequestParam double maxFee)
	{
		if(minFee>maxFee) 
		{
			ApiResponse<List<StudentDTO>>response=new ApiResponse<>(400,"Minimum fee cannot be greater than maximum fee",null);
			
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		List<StudentDTO>students=studentService.getStudentsByFeeRange(minFee, maxFee);
		
		ApiResponse<List<StudentDTO>>response=new ApiResponse<>(200,"Students fetched successfully",students);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}


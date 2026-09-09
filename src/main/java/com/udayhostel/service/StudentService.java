package com.udayhostel.service;

import com.udayhostel.entity.Room;

import com.udayhostel.repository.RoomRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.udayhostel.exception.ResourceNotFoundException;
import com.udayhostel.exception.RoomFullException;

import com.udayhostel.entity.Student;
import com.udayhostel.repository.StudentRepository;

import com.udayhostel.dto.StudentDTO;


@Service
public class StudentService 
{
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	public Student saveStudent(Student student)
	{
		int roomNo=student.getRoomNo();
		
		Room room=roomRepository.findById(roomNo).orElseThrow(()->new ResourceNotFoundException("Room not found with room number: "+roomNo));
		
		long occupied=studentRepository.countByRoomNo(roomNo);
		
		if(occupied>=room.getCapacity()) 
		{
			throw new RoomFullException("Room "+roomNo+" is full. Maximum capacity is "+room.getCapacity()+" students.");
		}
		
		return studentRepository.save(student);
	}
	
	public List<Student>getAllStudents()
	{
		return studentRepository.findAll();
	}
	
	public Student getStudentById(int id) 
	{
		return studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found with id: "+id));
	}
	
	public Student updateStudent(int id, Student student) 
	{
		Student existingStudent=studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id: "+id));
		
		int oldRoomNo=existingStudent.getRoomNo();
		int newRoomNo=student.getRoomNo();
		
		if(oldRoomNo!=newRoomNo) 
		{
			Room newRoom=roomRepository.findById(newRoomNo).orElseThrow(()->new ResourceNotFoundException("Room not found with room number: "+newRoomNo));
			
			int capacity=newRoom.getCapacity();
			
			long occupied=studentRepository.countByRoomNo(newRoomNo);
			
			if(occupied>=capacity) 
			{
				throw new RoomFullException("Room "+newRoomNo+" is full. Maximum capacity is "+capacity+" students.");
			}
		
		 }
			existingStudent.setName(student.getName());
			existingStudent.setMobile(student.getMobile());
			existingStudent.setCollege(student.getCollege());
			existingStudent.setRoomNo(student.getRoomNo());
			existingStudent.setJoiningDate(student.getJoiningDate());
			existingStudent.setFee(student.getFee());
			existingStudent.setAddress(student.getAddress());
			
			return studentRepository.save(existingStudent);
		

	}
	
	public String deleteStudent(int id) 
	{
		Student student=studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id: "+id));
		
			studentRepository.delete(student);
			
			return "Student deleted successfully";
		
	}
	
	public Student convertToEntity(StudentDTO dto) 
	{
		Student student=new Student();
		
		student.setStudentId(dto.getStudentId());
		student.setName(dto.getName());
		student.setMobile(dto.getMobile());
		student.setCollege(dto.getCollege());
		student.setRoomNo(dto.getRoomNo());
		student.setJoiningDate(dto.getJoiningDate());
		student.setFee(dto.getFee());
		student.setAddress(dto.getAddress());
		
		return student;
	}
	
	public StudentDTO convertToDTO(Student student) 
	{
		StudentDTO dto=new StudentDTO();
		
		dto.setStudentId(student.getStudentId());
		dto.setName(student.getName());
		dto.setMobile(student.getMobile());
		dto.setCollege(student.getCollege());
		dto.setRoomNo(student.getRoomNo());
		dto.setJoiningDate(student.getJoiningDate());
		dto.setFee(student.getFee());
		dto.setAddress(student.getAddress());
		
		return dto;
	}
	
	public List<StudentDTO>searchStudentsByName(String name)
	{
		List<Student> students=studentRepository.findByNameContainingIgnoreCase(name);
		
		return students.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public List<StudentDTO>getStudentsByRoomNo(int roomNo)
	{
		List<Student> students=studentRepository.findByRoomNo(roomNo);
		
		return students.stream()
				.map(this::convertToDTO)
				.toList();
		
	}
	
	public List<StudentDTO>getStudentsByCollege(String college)
	{
		List<Student> students=studentRepository.findByCollegeIgnoreCase(college);
		
		return students.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public List<StudentDTO>getStudentsByCollegeAndRoom(String college, int roomNo)
	{
		List<Student> students=studentRepository.findByCollegeIgnoreCaseAndRoomNo(college, roomNo);
		
		return students.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public Page<StudentDTO>getStudentsWithPagination(Pageable pageable)
	{
		Page<Student>students=studentRepository.findAll(pageable);
		
		return students.map(this::convertToDTO);
	}
	
	public List<StudentDTO>getStudentsByFee(double fee)
	{
		List<Student> students=studentRepository.findByFeeLessThanEqual(fee);
		
		return students.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public List<StudentDTO>getStudentsByFeeRange(double minFee, double maxFee)
	{
		List<Student> students=studentRepository.findByFeeBetween(minFee, maxFee);
		
		return students.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public boolean isRoomOccupied(int roomNo) 
	{
		return studentRepository.existsByRoomNo(roomNo);
	}
	
}

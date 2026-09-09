package com.udayhostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udayhostel.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer>
{
	List<Complaint>findByStudentId(int studentId);
	
	List<Complaint>findByStatusIgnoreCase(String status);
}

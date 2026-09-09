package com.udayhostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udayhostel.entity.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> 
{
	List<Enquiry>findByStatusIgnoreCase(String status);
	
	List<Enquiry>findByEmail(String email);

}

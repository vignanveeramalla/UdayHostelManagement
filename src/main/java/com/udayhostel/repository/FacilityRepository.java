package com.udayhostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udayhostel.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Integer>
{
	List<Facility>findByStatusIgnoreCase(String status);
	
	List<Facility>findByNameContainingIgnoreCase(String name);
}
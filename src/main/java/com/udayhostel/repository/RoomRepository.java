package com.udayhostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udayhostel.entity.Room; 

public interface RoomRepository extends JpaRepository<Room, Integer> 
{

}

package com.udayhostel.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.udayhostel.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
	List<Student>findByNameContainingIgnoreCase(String name);
	List<Student>findByRoomNo(int roomNo);
	List<Student>findByCollegeIgnoreCase(String college);
	List<Student>findByCollegeIgnoreCaseAndRoomNo(String college, int roomNo);
	List<Student>findByFeeLessThanEqual(double fee);
	List<Student>findByFeeBetween(double minFee, double maxFee);
	boolean existsByRoomNo(int roomNo);
	long countByRoomNo(int roomNo);
}
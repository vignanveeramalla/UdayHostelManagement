package com.udayhostel.repository;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.udayhostel.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> 
{
	List<Payment>findByStudentId(int studentId);
	
	List<Payment>findByStatusIgnoreCase(String status);
	
	@Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.studentId= :studentId")
	double getTotalPaidByStudentId(@Param("studentId") int studentId);
	
	//Get payments between two dates
	List<Payment>findByPaymentDateBetween(
			LocalDate startDate,
			LocalDate endDate);
}

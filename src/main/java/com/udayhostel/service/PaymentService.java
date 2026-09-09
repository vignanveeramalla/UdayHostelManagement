package com.udayhostel.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udayhostel.dto.PaymentDTO;
import com.udayhostel.dto.PaymentSummaryDTO;
import com.udayhostel.dto.MonthlyPaymentHistoryDTO;
import com.udayhostel.dto.MonthlyPaymentSummaryDTO;
import com.udayhostel.dto.MonthlyUnpaidPaymentDTO;
import com.udayhostel.entity.Payment;
import com.udayhostel.entity.Student;
import com.udayhostel.repository.PaymentRepository;
import com.udayhostel.repository.StudentRepository;
import com.udayhostel.exception.ResourceNotFoundException;

@Service
public class PaymentService 
{
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	//Convert DTO to Entity 
	public Payment convertToEntity(PaymentDTO dto) 
	{
		Payment payment =new Payment();
		
		payment.setStudentId(dto.getStudentId());
		payment.setAmount(dto.getAmount());
		payment.setPaymentDate(dto.getPaymentDate());
		payment.setPaymentMethod(dto.getPaymentMethod());
		payment.setStatus(dto.getStatus());
		
		return payment;
	}
	
	//Convert Entity to DTO
	public PaymentDTO convertToDTO(Payment payment) 
	{
		PaymentDTO dto =new PaymentDTO();
		
		dto.setPaymentId(payment.getPaymentId());
		dto.setStudentId(payment.getStudentId());
		dto.setAmount(payment.getAmount());
		dto.setPaymentDate(payment.getPaymentDate());
		dto.setPaymentMethod(payment.getPaymentMethod());
		dto.setStatus(payment.getStatus());
		
		return dto;
		
	}
	
	//save Payment
	private  void validatePayment(Payment payment) 
	{
		int studentId=payment.getStudentId();
		
	    studentRepository.findById(studentId).orElseThrow(()->new IllegalArgumentException("Student not found with id: "+studentId));
	    
	    String paymentMethod=payment.getPaymentMethod().toUpperCase();
	    
	    if(!paymentMethod.equals("CASH") &&
	       !paymentMethod.equals("UPI") &&
	       !paymentMethod.equals("BANK_TRANSFER")) 
	    {
	    	throw new IllegalArgumentException("Invalid payment method. Use CASH, UPI or BANK_TRANSFER");
	    }
	    
	    //Validate payment status
	    String status=payment.getStatus().toUpperCase();
	    
	    if(!status.equals("PAID") &&
	       !status.equals("PENDING") &&
	       !status.equals("PARTIAL")) 
	    {
	    	throw new IllegalArgumentException("Invalid payment status. Use PAID, PENDING or PARTIAL");
	    }
	    
	    payment.setPaymentMethod(paymentMethod);
	    payment.setStatus(status);
		
	
	}
	
	//Save Payment
	public Payment savePayment(Payment payment) 
	{
		validatePayment(payment);
		
		return paymentRepository.save(payment);
	}
	
	//Get all payments
	public List<Payment>getAllPayments()
	{
		return paymentRepository.findAll();
	}
	
	//Get Payment history of a student
	public List<PaymentDTO>getPaymentsByStudentId(int studentId)
	{
		//First check student exits
		studentRepository.findById(studentId).orElseThrow(()->new IllegalArgumentException("Student not found with id: "+studentId));
		
		List<Payment>payments=paymentRepository.findByStudentId(studentId);
		
		return payments.stream().map(this::convertToDTO).toList();
	}
	
	//Get payments by Status
	public List<PaymentDTO>getPaymentsByStatus(String status)
	{
		String paymentStatus=status.toUpperCase();
		
		if(!paymentStatus.equals("PAID")&&
		   !paymentStatus.equals("PENDING")&&
		   !paymentStatus.equals("PARTIAL")) 
		{
			throw new IllegalArgumentException("Invalid payment status. Use PAID, PENDING or PARTIAL");
		}
		List<Payment>payments=paymentRepository.findByStatusIgnoreCase(paymentStatus);
		
		return payments.stream().map(this::convertToDTO).toList();
	}
	
	public PaymentSummaryDTO getPaymentSummary(int studentId) 
	{
		Student student=studentRepository.findById(studentId).orElseThrow(()->new IllegalArgumentException("Student not found with id: "+studentId));
		
		double totalFee=student.getFee();
		
		double totalPaid=paymentRepository.getTotalPaidByStudentId(studentId);
		
		double balance=totalFee-totalPaid;
		
		String paymentStatus;
		
		if(balance<=0) 
		{
			balance=0;
			paymentStatus="PAID";
		}
		else if(totalPaid>0) 
		{
			paymentStatus="PARTIAL";
		}
		else 
		{
			paymentStatus="PENDING";
		}
		
		return new PaymentSummaryDTO(studentId,totalFee,totalPaid,balance,paymentStatus);
	}
	
	public Payment getPaymentById(int id) 
	{
		return paymentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Payment not found with id: "+id));
	}
	
	public Payment updatePayment(int id, Payment payment) 
	{
		Payment existingPayment=paymentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Payment not found with id: "+id));
		
		validatePayment(payment);
		
		existingPayment.setAmount(payment.getAmount());
		existingPayment.setPaymentDate(payment.getPaymentDate());
		existingPayment.setPaymentMethod(payment.getPaymentMethod());
		existingPayment.setStatus(payment.getStatus());
		existingPayment.setStudentId(payment.getStudentId());
		
		return paymentRepository.save(existingPayment);
	}
	
	public String deletePayment(int id) 
	{
		Payment payment=paymentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Payment not found with id: "+id));
		
		paymentRepository.delete(payment);
		
		return "Payment deleted successfully";
	}
	
	public MonthlyPaymentSummaryDTO getMonthlyPaymentSummary(int year, int month) 
	{
		YearMonth yearMonth;
		
		try 
		{
			yearMonth=YearMonth.of(year, month);
		}
		catch(Exception e) 
		{
			throw new IllegalArgumentException("Invalid year or month");
		}
		
		LocalDate startDate=yearMonth.atDay(1);
		LocalDate endDate=yearMonth.atEndOfMonth();
		
		List<Student>students=studentRepository.findAll();
		
		List<Payment>payments=paymentRepository.findByPaymentDateBetween(startDate, endDate);
		
		Map<Integer, Double>paidAmountByStudent=new HashMap<>();
		
		for(Payment payment:payments) 
		{
			String status=payment.getStatus();
			
			if(status!=null&&
					(status.equalsIgnoreCase("PAID")
					||status.equalsIgnoreCase("PARTIAL"))) 
			{
				paidAmountByStudent.merge(payment.getStudentId(),payment.getAmount(),Double::sum);
			}
		}
		int totalStudents=0;
		int paidStudents=0;
		int partialStudents=0;
		int unpaidStudents=0;
		
		double totalExpectedFee=0;
		double totalCollected=0;
		double totalPending=0;
		
		for(Student student:students) 
		{
			//Don't count students who joined after this month
			if(student.getJoiningDate()!=null&&student.getJoiningDate().isAfter(endDate)) 
			{
				continue;
			}
			totalStudents++;
			
			double fee=student.getFee();
			
			double paid=paidAmountByStudent.getOrDefault(student.getStudentId(), 0.0);
			
			double balance=Math.max(fee-paid, 0);
			
			totalExpectedFee+=fee;
			totalCollected+=Math.min(paid, fee);
			totalPending+=balance;
			
			if(paid>=fee) 
			{
				paidStudents++;
			}
			else if(paid>0) 
			{
				partialStudents++;
			}
			else 
			{
				unpaidStudents++;
			}
		}
		return new MonthlyPaymentSummaryDTO(year,month,totalStudents,paidStudents,partialStudents,unpaidStudents,totalExpectedFee,totalCollected,totalPending);
	}
	
	public List<MonthlyUnpaidPaymentDTO> getMonthlyUnpaidPayments(
	        int year, int month) 
	{

	    YearMonth yearMonth;

	    try 
	    {
	        yearMonth = YearMonth.of(year, month);
	    } 
	    catch (Exception e) 
	    {
	        throw new IllegalArgumentException(
	                "Invalid year or month");
	    }

	    LocalDate startDate = yearMonth.atDay(1);
	    LocalDate endDate = yearMonth.atEndOfMonth();

	    List<Student> students = studentRepository.findAll();

	    List<Payment> payments =paymentRepository.findByPaymentDateBetween(startDate, endDate);

	    Map<Integer, Double> paidAmountByStudent = new HashMap<>();

	    for (Payment payment : payments) 
	    {

	        String status = payment.getStatus();

	        if (status != null &&
	                (status.equalsIgnoreCase("PAID")
	                || status.equalsIgnoreCase("PARTIAL"))) 
	        {

	            paidAmountByStudent.merge(
	                    payment.getStudentId(),
	                    payment.getAmount(),
	                    Double::sum);
	        }
	    }

	    List<MonthlyUnpaidPaymentDTO> unpaidStudents =
	            new ArrayList<>();

	    for (Student student : students) 
	    {

	        if (student.getJoiningDate() != null &&
	                student.getJoiningDate().isAfter(endDate)) 
	        {
	            continue;
	        }

	        double fee = student.getFee();

	        double paid = paidAmountByStudent.getOrDefault(
	                student.getStudentId(), 0.0);

	        double balance = Math.max(fee - paid, 0);

	        // Include both NOT PAID and PARTIAL
	        if (paid < fee) 
	        {

	            String status;

	            if (paid == 0) 
	            {
	                status = "NOT PAID";
	            } 
	            else 
	            {
	                status = "PARTIAL";
	            }

	            unpaidStudents.add(
	                    new MonthlyUnpaidPaymentDTO(
	                            student.getStudentId(),
	                            student.getName(),
	                            student.getRoomNo(),
	                            fee,
	                            paid,
	                            balance,
	                            status));
	        }
	    }

	    return unpaidStudents;
	}
	
	public List<MonthlyPaymentHistoryDTO> getMonthlyPaymentHistory(int year, int month) 
	{

	    YearMonth yearMonth;

	    try 
	    {
	        yearMonth = YearMonth.of(year, month);
	    } 
	    catch (Exception e) 
	    {
	        throw new IllegalArgumentException("Invalid year or month");
	    }

	    LocalDate startDate = yearMonth.atDay(1);
	    LocalDate endDate = yearMonth.atEndOfMonth();

	    List<Payment> payments =paymentRepository.findByPaymentDateBetween(startDate, endDate);

	    List<MonthlyPaymentHistoryDTO> history =new ArrayList<>();

	    for (Payment payment : payments) 
	    {

	        Student student = studentRepository.findById(
	                payment.getStudentId()).orElse(null);

	        String studentName =
	                student != null ? student.getName() : "Unknown";

	        history.add(
	                new MonthlyPaymentHistoryDTO(
	                        payment.getPaymentId(),
	                        payment.getStudentId(),
	                        studentName,
	                        payment.getAmount(),
	                        payment.getPaymentDate(),
	                        payment.getPaymentMethod(),
	                        payment.getStatus()));
	    }

	    return history;
	}
}

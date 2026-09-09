package com.udayhostel.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.udayhostel.dto.PaymentDTO;
import com.udayhostel.dto.PaymentSummaryDTO;
import com.udayhostel.entity.Payment;
import com.udayhostel.service.PaymentService;
import com.udayhostel.response.ApiResponse;
import com.udayhostel.dto.MonthlyPaymentHistoryDTO;
import com.udayhostel.dto.MonthlyPaymentSummaryDTO;
import com.udayhostel.dto.MonthlyUnpaidPaymentDTO;

@RestController
@RequestMapping("/payments")
public class PaymentController 
{
	@Autowired
	private PaymentService paymentService;
	
	//Add Payment
	@PostMapping
	public ResponseEntity<ApiResponse<PaymentDTO>>addPayment(@Valid @RequestBody PaymentDTO dto)
	{
		Payment payment=paymentService.convertToEntity(dto);
		
		Payment savedPayment=paymentService.savePayment(payment);
		
		PaymentDTO responseDTO=paymentService.convertToDTO(savedPayment);
		
		ApiResponse<PaymentDTO>response=new ApiResponse<>(201,"Payment recorded successfully",responseDTO);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
			
	}
	
	//Get all payments
	@GetMapping
	public ResponseEntity<ApiResponse<List<PaymentDTO>>>getAllPayments()
	{
		List<Payment>payments=paymentService.getAllPayments();
		
		List<PaymentDTO>paymentDTOs=payments.stream().map(paymentService::convertToDTO).toList();
		
		ApiResponse<List<PaymentDTO>>response=new ApiResponse<>(200,"Payments fetched successfully",paymentDTOs);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Get Payment history of a student
	@GetMapping("/student/{studentId}")
	public ResponseEntity<ApiResponse<List<PaymentDTO>>>getPaymentsByStudentId(@PathVariable int studentId)
	{
		List<PaymentDTO>payments=paymentService.getPaymentsByStudentId(studentId);
		
		ApiResponse<List<PaymentDTO>> response=new ApiResponse<>(200,"Students payment history fetched successfully",payments);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//Get payments by status
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<PaymentDTO>>>getPaymentsByStatus(@PathVariable String status)
	{
		List<PaymentDTO>payments=paymentService.getPaymentsByStatus(status);
		
		ApiResponse<List<PaymentDTO>>response=new ApiResponse<>(200,"Payments fetched successfully",payments);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/summary/{studentId}")
	public ResponseEntity<ApiResponse<PaymentSummaryDTO>>getPaymentSummary(@PathVariable int studentId)
	{
		PaymentSummaryDTO summary=paymentService.getPaymentSummary(studentId);
		
		ApiResponse<PaymentSummaryDTO>response=new ApiResponse<>(200,"Payment summary fetched successfully",summary);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	// Monthly Payment Summary
	@GetMapping("/monthly-summary")
	public ResponseEntity<ApiResponse<MonthlyPaymentSummaryDTO>>
	getMonthlyPaymentSummary(
	        @RequestParam int year,
	        @RequestParam int month) {

	    MonthlyPaymentSummaryDTO summary =
	            paymentService.getMonthlyPaymentSummary(year, month);

	    ApiResponse<MonthlyPaymentSummaryDTO> response =
	            new ApiResponse<>(
	                    200,
	                    "Monthly payment summary fetched successfully",
	                    summary);

	    return new ResponseEntity<>(
	            response, HttpStatus.OK);
	}
	
	// Monthly Unpaid / Partial Students
	@GetMapping("/monthly-unpaid")
	public ResponseEntity<ApiResponse<List<MonthlyUnpaidPaymentDTO>>>
	getMonthlyUnpaidPayments(
	        @RequestParam int year,
	        @RequestParam int month) {

	    List<MonthlyUnpaidPaymentDTO> unpaid =
	            paymentService.getMonthlyUnpaidPayments(year, month);

	    ApiResponse<List<MonthlyUnpaidPaymentDTO>> response =
	            new ApiResponse<>(
	                    200,
	                    "Monthly unpaid students fetched successfully",
	                    unpaid);

	    return new ResponseEntity<>(
	            response, HttpStatus.OK);
	}
	
	// Monthly Payment History
	@GetMapping("/monthly-history")
	public ResponseEntity<ApiResponse<List<MonthlyPaymentHistoryDTO>>>
	getMonthlyPaymentHistory(
	        @RequestParam int year,
	        @RequestParam int month) {

	    List<MonthlyPaymentHistoryDTO> history =
	            paymentService.getMonthlyPaymentHistory(year, month);

	    ApiResponse<List<MonthlyPaymentHistoryDTO>> response =
	            new ApiResponse<>(
	                    200,
	                    "Monthly payment history fetched successfully",
	                    history);

	    return new ResponseEntity<>(
	            response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PaymentDTO>>getPaymentById(@PathVariable int id)
	{
		Payment payment=paymentService.getPaymentById(id);
		
		PaymentDTO dto=paymentService.convertToDTO(payment);
		
		ApiResponse<PaymentDTO>response=new ApiResponse<>(200, "Payment fetched successfully",dto);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Update Payment
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PaymentDTO>>updatePayment(@PathVariable int id,@Valid @RequestBody PaymentDTO dto)
	{
		Payment payment=paymentService.convertToEntity(dto);
		
		Payment updatePayment=paymentService.updatePayment(id, payment);
		
		PaymentDTO responseDTO=paymentService.convertToDTO(updatePayment);
		
		ApiResponse<PaymentDTO> response=new ApiResponse<>(200, "Payment updated successfully", responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Delete Payment
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>>deletePayment(@PathVariable int id)
	{
		String message=paymentService.deletePayment(id);
		
		ApiResponse<String>response=new ApiResponse<>(200,message,null);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}

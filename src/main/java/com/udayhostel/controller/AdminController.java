package com.udayhostel.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.udayhostel.dto.AdminDTO;
import com.udayhostel.entity.Admin;
import com.udayhostel.service.AdminService;
import com.udayhostel.response.ApiResponse;
import com.udayhostel.dto.ChangePasswordDTO;

@RestController
@RequestMapping("/admins")
public class AdminController 
{

	@Autowired
	private AdminService adminService;
	
	//Register Admin
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AdminDTO>>registerAdmin(@Valid @RequestBody AdminDTO dto)
	{
		Admin admin=adminService.convertToEntity(dto);
		
		Admin savedAdmin=adminService.registerAdmin(admin);
		
		AdminDTO responseDTO=adminService.convertToDTO(savedAdmin);
		
		ApiResponse<AdminDTO>response=new ApiResponse<>(201,"Admin registered successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//Get Admin By Email
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<AdminDTO>>getAdminByEmail(@PathVariable("email")String email)
	{
		Admin admin=adminService.getAdminByEmail(email);
		
		AdminDTO responseDTO=adminService.convertToDTO(admin);
		
		ApiResponse<AdminDTO>response=new ApiResponse<>(200,"Admin fetched successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Get Admin By Id
	@GetMapping("/{adminId}")
	public ResponseEntity<ApiResponse<AdminDTO>> getAdminById(
	        @PathVariable("adminId") int adminId) {

	    Admin admin = adminService.getAdminById(adminId);

	    AdminDTO responseDTO = adminService.convertToDTO(admin);

	    ApiResponse<AdminDTO> response =new ApiResponse<>(200, "Admin fetched successfully", responseDTO);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Update Admin
	@PutMapping("/{adminId}")
	public ResponseEntity<ApiResponse<AdminDTO>>updateAdmin(@PathVariable("adminId")int adminId,@Valid@RequestBody AdminDTO dto)
	{
		Admin updatedAdmin=adminService.updateAdmin(adminId,dto);
		
		AdminDTO responseDTO=adminService.convertToDTO(updatedAdmin);
		
		ApiResponse<AdminDTO>response=new ApiResponse<>(200,"Admin updated successfully",responseDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Change Password
	@PutMapping("/{adminId}/password")
	public ResponseEntity<ApiResponse<String>>changePassword(@PathVariable("adminId")int adminId,@Valid @RequestBody ChangePasswordDTO dto)
	{
		String message=adminService.changePassword(adminId,dto);
		
		ApiResponse<String>response=new ApiResponse<>(200,message,null);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Delete Admin
	@DeleteMapping("/{adminId}")
	public ResponseEntity<ApiResponse<String>>deleteAdmin(@PathVariable("adminId")int adminId)
	{
		String message=adminService.deleteAdmin(adminId);
		
		ApiResponse<String>response=new ApiResponse<>(200,message,null);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<java.util.List<AdminDTO>>>getAllAdmins()
	{
		java.util.List<AdminDTO>admins=adminService.getAllAdmins();
		
		ApiResponse<java.util.List<AdminDTO>>response=new ApiResponse<>(200,"Admins fetched successfully",admins);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}


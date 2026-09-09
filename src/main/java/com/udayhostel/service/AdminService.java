package com.udayhostel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.udayhostel.dto.AdminDTO;
import com.udayhostel.entity.Admin;
import com.udayhostel.repository.AdminRepository;
import com.udayhostel.exception.ResourceNotFoundException;
import com.udayhostel.dto.ChangePasswordDTO;

@Service
public class AdminService 
{

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired	
	private PasswordEncoder passwordEncoder;
	
	//Convert DTO to Entity
	public Admin convertToEntity(AdminDTO dto) 
	{
		Admin admin=new Admin();
		
		admin.setName(dto.getName());
		admin.setEmail(dto.getEmail());
		admin.setPassword(dto.getPassword());
		
		return admin;
	}
	
	//Convert Entity to DTO
	public AdminDTO convertToDTO(Admin admin) 
	{
		AdminDTO dto=new AdminDTO();
		
		dto.setAdminId(admin.getAdminId());
		dto.setName(admin.getName());
		dto.setEmail(admin.getEmail());
		dto.setRole(admin.getRole());
		
		return dto;
	}
	
	//Register Admin
	public Admin registerAdmin(Admin admin) 
	{
		//check weather email already exists
		if(adminRepository.existsByEmail(admin.getEmail())) 
		{
			throw new IllegalArgumentException("Admin already exists with email: "+admin.getEmail());
		}
		
		admin.setRole("ADMIN");
		
		String encodedPasssword=passwordEncoder.encode(admin.getPassword());
		
		admin.setPassword(encodedPasssword);
		
		return adminRepository.save(admin);
	}
	
	//Get Admin By Email
	public Admin getAdminByEmail(String email) 
	{
		return adminRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("Admin not found with email: "+email));
	}
	

	
	public java.util.List<AdminDTO>getAllAdmins()
	{
		return adminRepository.findAll()
				.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public Admin getAdminById(int adminId) 
	{
		return adminRepository.findById(adminId).orElseThrow(()->new ResourceNotFoundException("Admin not found with id: "+adminId));
	}
	
	public Admin updateAdmin(int adminId,AdminDTO dto) 
	{
		Admin admin=getAdminById(adminId);
		
		admin.setName(dto.getName());
		admin.setEmail(dto.getEmail());
		
		if(dto.getPassword()!=null&&!dto.getPassword().isBlank()) 
		{
			admin.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		
		return adminRepository.save(admin);
	}
	
	public String changePassword(int adminId, ChangePasswordDTO dto) 
	{
		Admin admin=getAdminById(adminId);
		
		if(!passwordEncoder.matches(dto.getCurrentPassword(), admin.getPassword())) 
		{
			throw new IllegalArgumentException("Current password is incorrect");
			
		}
		if(dto.getCurrentPassword().equals(dto.getNewPassword())) 
		{
			throw new IllegalArgumentException("New Password must be different from current password");
		}
			admin.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			
			adminRepository.save(admin);
			
			return "Password changed successfully";
			
	}
	
	
	public String deleteAdmin(int adminId) 
	{
		Admin admin=getAdminById(adminId);
		
		adminRepository.delete(admin);
		
		return "Admin deleted successfully";
	}
}

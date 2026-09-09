package com.udayhostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udayhostel.dto.DashboardDTO;
import com.udayhostel.response.ApiResponse;
import com.udayhostel.service.DashboardService;


@RestController
@RequestMapping("/dashboard")
public class DashboardController 
{
	@Autowired
	private DashboardService dashboardService;
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<DashboardDTO>>getDashboard()
	{
		DashboardDTO dashboard=dashboardService.getDashboardStatistics();
		
		ApiResponse<DashboardDTO>response=new ApiResponse<>(200,"Dashboard statistics fetched successfully",dashboard);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

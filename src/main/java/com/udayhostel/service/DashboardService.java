package com.udayhostel.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.udayhostel.dto.DashboardDTO;
import com.udayhostel.entity.Student;
import com.udayhostel.entity.Room;
import com.udayhostel.entity.Payment;
import com.udayhostel.entity.Complaint;
import com.udayhostel.entity.Enquiry;

import com.udayhostel.repository.StudentRepository;
import com.udayhostel.repository.RoomRepository;
import com.udayhostel.repository.PaymentRepository;
import com.udayhostel.repository.ComplaintRepository;
import com.udayhostel.repository.EnquiryRepository;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;


    public DashboardDTO getDashboardStatistics() {

        // Total students
        long totalStudents = studentRepository.count();


        // Total rooms
        long totalRooms = roomRepository.count();


        // Occupied rooms
        long totalOccupiedRooms = studentRepository.findAll()
                .stream()
                .map(Student::getRoomNo)
                .distinct()
                .count();


        // Available rooms
        long totalAvailableRooms = totalRooms - totalOccupiedRooms;


        // Total fees
        double totalFees = studentRepository.findAll()
                .stream()
                .mapToDouble(Student::getFee)
                .sum();


        // Total paid amount
        double totalPaid = paymentRepository.findAll()
                .stream()
                .mapToDouble(Payment::getAmount)
                .sum();


        // Total pending amount
        double totalPending = totalFees - totalPaid;


        // Total complaints
        long totalComplaints = complaintRepository.count();


        // Open complaints
        long openComplaints = complaintRepository.findAll()
                .stream()
                .filter(complaint ->
                        "OPEN".equalsIgnoreCase(complaint.getStatus()))
                .count();


        // Pending enquiries
        long pendingEnquiries = enquiryRepository.findAll()
                .stream()
                .filter(enquiry ->
                        "PENDING".equalsIgnoreCase(enquiry.getStatus()))
                .count();


        return new DashboardDTO(
                totalStudents,
                totalRooms,
                totalOccupiedRooms,
                totalAvailableRooms,
                totalFees,
                totalPaid,
                totalPending,
                totalComplaints,
                openComplaints,
                pendingEnquiries
        );
    }
}
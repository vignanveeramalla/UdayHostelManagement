package com.udayhostel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udayhostel.dto.EnquiryDTO;
import com.udayhostel.entity.Enquiry;
import com.udayhostel.exception.ResourceNotFoundException;
import com.udayhostel.repository.EnquiryRepository;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;
    
    @Autowired
    private EmailService emailService;


    // ================= CONVERT ENTITY TO DTO =================

    public EnquiryDTO convertToDTO(Enquiry enquiry) {

        EnquiryDTO dto = new EnquiryDTO();

        dto.setEnquiryId(enquiry.getEnquiryId());
        dto.setName(enquiry.getName());
        dto.setEmail(enquiry.getEmail());
        dto.setMessage(enquiry.getMessage());
        dto.setStatus(enquiry.getStatus());
        dto.setAdminReply(enquiry.getAdminReply());

        return dto;
    }
    
 // ================= ADD ENQUIRY =================

    public EnquiryDTO addEnquiry(EnquiryDTO dto) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (dto.getMessage() == null || dto.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        Enquiry enquiry = new Enquiry();

        enquiry.setName(dto.getName().trim());
        enquiry.setEmail(dto.getEmail().trim());
        enquiry.setMessage(dto.getMessage().trim());

        // New enquiry starts as PENDING
        enquiry.setStatus("PENDING");

        enquiry.setAdminReply(null);

        Enquiry savedEnquiry =
                enquiryRepository.save(enquiry);

        return convertToDTO(savedEnquiry);
    }

    // ================= GET ALL ENQUIRIES =================

    public List<EnquiryDTO> getAllEnquiries() {

        return enquiryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // ================= GET BY ID =================

    public EnquiryDTO getEnquiryById(int enquiryId) {

        Enquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Enquiry not found with id: " + enquiryId
                    )
                );

        return convertToDTO(enquiry);
    }


    // ================= GET BY STATUS =================

    public List<EnquiryDTO> getEnquiriesByStatus(String status) {

        validateStatus(status);

        return enquiryRepository
                .findByStatusIgnoreCase(status)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // ================= GET BY EMAIL =================

    public List<EnquiryDTO> getEnquiriesByEmail(String email) {

        return enquiryRepository
                .findByEmail(email)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // ================= UPDATE STATUS =================

    public EnquiryDTO updateStatus(
            int enquiryId,
            String status) {

        validateStatus(status);

        Enquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Enquiry not found with id: " + enquiryId
                    )
                );

        enquiry.setStatus(status.toUpperCase());

        Enquiry updated =
                enquiryRepository.save(enquiry);

        return convertToDTO(updated);
    }


    // ================= ADMIN REPLY =================

    public EnquiryDTO replyToEnquiry(int enquiryId, String reply) 
    {

        if (reply == null || reply.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Reply cannot be empty");
        }

        Enquiry enquiry = enquiryRepository.findById(enquiryId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Enquiry not found with id: " + enquiryId));

        String cleanReply = reply.trim();

        // Send email first
        emailService.sendEnquiryReply(
            enquiry.getEmail(),
            enquiry.getName(),
            cleanReply
        );

        // Save reply only after email is sent successfully
        enquiry.setAdminReply(cleanReply);

        if ("PENDING".equalsIgnoreCase(enquiry.getStatus())) 
        {
            enquiry.setStatus("CONTACTED");
        }

        Enquiry savedEnquiry = enquiryRepository.save(enquiry);

        return convertToDTO(savedEnquiry);
    }

    // ================= DELETE =================

    public String deleteEnquiry(int enquiryId) {

        Enquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Enquiry not found with id: " + enquiryId
                    )
                );

        enquiryRepository.delete(enquiry);

        return "Enquiry deleted successfully";
    }


    // ================= STATUS VALIDATION =================

    private void validateStatus(String status) {

        if (status == null) {

            throw new IllegalArgumentException(
                "Status is required"
            );
        }

        String value = status.toUpperCase();

        if (!value.equals("PENDING")
                && !value.equals("CONTACTED")
                && !value.equals("CLOSED")) {

            throw new IllegalArgumentException(
                "Invalid status. Use PENDING, CONTACTED or CLOSED"
            );
        }
    }
}
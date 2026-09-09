package com.udayhostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udayhostel.dto.EnquiryDTO;
import com.udayhostel.response.ApiResponse;
import com.udayhostel.service.EnquiryService;

@RestController
@RequestMapping("/enquiries")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

 // ================= ADD ENQUIRY =================

    @PostMapping
    public ResponseEntity<ApiResponse<EnquiryDTO>> addEnquiry(
            @RequestBody EnquiryDTO dto) {

        EnquiryDTO savedEnquiry =
                enquiryService.addEnquiry(dto);

        ApiResponse<EnquiryDTO> response =
                new ApiResponse<>(
                    201,
                    "Enquiry submitted successfully",
                    savedEnquiry
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // ================= GET ALL =================

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnquiryDTO>>> getAllEnquiries() {

        List<EnquiryDTO> enquiries =
                enquiryService.getAllEnquiries();

        ApiResponse<List<EnquiryDTO>> response =
                new ApiResponse<>(
                    200,
                    "Enquiries fetched successfully",
                    enquiries
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= GET BY ID =================

    @GetMapping("/{enquiryId}")
    public ResponseEntity<ApiResponse<EnquiryDTO>> getEnquiryById(
            @PathVariable int enquiryId) {

        EnquiryDTO enquiry =
                enquiryService.getEnquiryById(enquiryId);

        ApiResponse<EnquiryDTO> response =
                new ApiResponse<>(
                    200,
                    "Enquiry fetched successfully",
                    enquiry
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= GET BY STATUS =================

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<EnquiryDTO>>> getByStatus(
            @PathVariable String status) {

        List<EnquiryDTO> enquiries =
                enquiryService.getEnquiriesByStatus(status);

        ApiResponse<List<EnquiryDTO>> response =
                new ApiResponse<>(
                    200,
                    "Enquiries fetched successfully",
                    enquiries
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= GET BY EMAIL =================

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<EnquiryDTO>>> getByEmail(
            @PathVariable String email) {

        List<EnquiryDTO> enquiries =
                enquiryService.getEnquiriesByEmail(email);

        ApiResponse<List<EnquiryDTO>> response =
                new ApiResponse<>(
                    200,
                    "Enquiries fetched successfully",
                    enquiries
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= UPDATE STATUS =================

    @PutMapping("/{enquiryId}/status")
    public ResponseEntity<ApiResponse<EnquiryDTO>> updateStatus(
            @PathVariable int enquiryId,
            @RequestParam String status) {

        EnquiryDTO updated =
                enquiryService.updateStatus(
                    enquiryId,
                    status
                );

        ApiResponse<EnquiryDTO> response =
                new ApiResponse<>(
                    200,
                    "Enquiry status updated successfully",
                    updated
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= ADMIN REPLY =================

    @PutMapping("/{enquiryId}/reply")
    public ResponseEntity<ApiResponse<EnquiryDTO>> replyToEnquiry(
            @PathVariable int enquiryId,
            @RequestBody ReplyRequest request) {

        EnquiryDTO updated =
                enquiryService.replyToEnquiry(
                    enquiryId,
                    request.getReply()
                );

        ApiResponse<EnquiryDTO> response =
                new ApiResponse<>(
                    200,
                    "Reply saved successfully",
                    updated
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= DELETE =================

    @DeleteMapping("/{enquiryId}")
    public ResponseEntity<ApiResponse<String>> deleteEnquiry(
            @PathVariable int enquiryId) {

        String message =
                enquiryService.deleteEnquiry(enquiryId);

        ApiResponse<String> response =
                new ApiResponse<>(
                    200,
                    message,
                    null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    // ================= REPLY REQUEST =================

    public static class ReplyRequest {

        private String reply;


        public ReplyRequest() {
        }


        public String getReply() {
            return reply;
        }


        public void setReply(String reply) {
            this.reply = reply;
        }
    }
}
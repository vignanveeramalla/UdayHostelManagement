package com.udayhostel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService 
{
	@Autowired
	public JavaMailSender mailSender;
	
	public void sendEnquiryReply(String toEmail, String studentName, String reply) 
	{
		SimpleMailMessage message=new SimpleMailMessage();
		
		message.setTo(toEmail);
		
		message.setSubject("Reply from Uday Hostel");
		
		message.setText(
				"Dear "+studentName+ ",\n\n"
				+reply
				+"\n\n"
				+"Regards,\n"
				+"Uday Hostel Management");
		
		mailSender.send(message);
		
	}

}

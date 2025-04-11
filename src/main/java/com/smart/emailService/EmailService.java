package com.smart.emailService;

import java.util.Properties;


import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	public Boolean sendEmail(String to, String subject, String message) {
		String from = "ajayraj325729@gmail.com";
		Boolean f = false;
//		Set properties for the gmail 
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

//	    Step:1 Get Session 
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("ajayraj325729@gmail.com", "yjhy mrwqqlvx fqwd");
			}

		});
		session.setDebug(true);
//	   Step:2 Compose message 
		MimeMessage mimeMessage = new MimeMessage(session);
		
		try {
//	    From Gmail 
			mimeMessage.setFrom(new InternetAddress(from));
//		Adding recipient to message
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//      sending subject
			mimeMessage.setSubject(subject);
//		sending message
			mimeMessage.setText(message);

//		Step: 3 Sending the message using the Transport class
			Transport.send(mimeMessage); 
			f=true;			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		return f;
	}

}

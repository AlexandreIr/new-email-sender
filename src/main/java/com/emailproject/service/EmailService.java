package com.emailproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.emailproject.model.Email;
import com.emailproject.util.LogUtil;

@Stateless
public class EmailService extends Thread {
	
	public static final String HEADER_HTML = "text/html; charset=utf-8";
	
	private List<Email> emailList = new ArrayList<>();
	
	public void send(Email email) {
		emailList.add(email);
		sender();
	}
	
	public void send(List<Email> emails) {
		emailList = emails;
		sender();
	}
	
	private EmailService copy() {
		EmailService emailService = new EmailService();
		emailService.emailList = emailList;
		return emailService;
	}
	
	private void sender() {
		new Thread(this.copy()).start();
	}
	
	@Override
	public void run() {
		Properties properties = new Properties();
		
		
		
		properties.put("mail.smtp.starttls", true);
		properties.put("mail.smtp.host", System.getProperty("email-project.mail.smtp.host"));
		properties.put("mail.smtp.port", System.getProperty("email-project.mail.smtp.port"));
		
		Session session = Session.getInstance(properties);
		session.setDebug(false);
		
		for(Email e: emailList){
			
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(System.getProperty("email-project.mail.from")));
				
				if(e.getTo().contains("/")) {
					List<InternetAddress> emailAdr = new ArrayList<>();
					for(String em: e.getTo().split("/")) {
						emailAdr.add(new InternetAddress(em));
					}
					message.addRecipients(Message.RecipientType.TO, emailAdr.toArray(new InternetAddress[0]));
				} else {
					InternetAddress para = new InternetAddress(e.getTo());
					message.addRecipient(Message.RecipientType.TO, para);
				}
				
				message.setSubject(e.getSubject());
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setHeader("Content-type", HEADER_HTML);
				textPart.setContent(e.getText(), HEADER_HTML);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(textPart);
				message.setContent(multipart);
				Transport.send(message);
			} catch (MessagingException err) {
				LogUtil.getLogger(EmailService.class).error("Erro ao enviar e-mail: "+err.getMessage());
			}
			
		}
	}
}

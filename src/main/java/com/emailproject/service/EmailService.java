package com.emailproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.emailproject.model.Email;
import com.emailproject.util.LogUtil;

@Stateless
public class EmailService extends Thread {
	
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
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(System.getProperty("email-project.mail.from")));
		} catch (MessagingException e) {
			LogUtil.getLogger(EmailService.class).error("Erro ao enviar e-mail: "+e.getMessage());
		}
		
		Multipart multi = new MimeMultipart();
	}
}

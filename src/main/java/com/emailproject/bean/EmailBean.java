package com.emailproject.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.emailproject.dto.EmailLayout;
import com.emailproject.model.Email;
import com.emailproject.service.EmailService;

@Named
@RequestScoped
public class EmailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmailService emailService;
	
	public String sendEmail() {
		emailService.send(buildEmail());
		return null;
	}
	
	private Email buildEmail() {
		EmailLayout layout = new EmailLayout();
		return layout.emailBuilderAdm("alex.silva250@hotmail.com", "Cadastro aprovado!");
	}

}

package com.emailproject.dto;

import com.emailproject.model.Email;

public class EmailLayout {
	public Email emailBuilderAdm(String to, String subject) {
		StringBuilder text = new StringBuilder();

		text.append("A/C do administrador").append("<br><br>").append("Solicito alteração de senha").append("<br><br>");
		footerGenerator(text);
		generateSignature(text, "Alexandre");

		return new Email(to, subject, text.toString());
	}

	private String footerGenerator(StringBuilder text) {
		return text.append("E-mail autmático, por favor não responde").toString();
	}

	private String generateSignature(StringBuilder text, String signature) {
		return text.append("<br><br>").append(signature).toString();
	}
}

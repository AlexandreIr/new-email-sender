package com.emailproject.dto;

import com.emailproject.model.Email;

public class EmailLayout {
	public Email emailBuilderAdm(String to, String subject) {
		StringBuilder text = new StringBuilder();
		return new Email(to, subject, text.toString());
	}
}

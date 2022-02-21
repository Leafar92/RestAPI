package com.challenge.food.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.challenge.food.domain.service.EmailService;
import com.challenge.food.infrastructure.email.EnvioEmailFake;
import com.challenge.food.infrastructure.email.SandBoxEmailService;
import com.challenge.food.infrastructure.email.SmtpEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties properties;

	@Bean
	public EmailService enviar() {
		switch (properties.getImpl().toString()) {
		case "FAKE":
			return new EnvioEmailFake();

		case "SMTP":
			return new SmtpEmailService();

		case "SANDBOX":
			return new SandBoxEmailService();

		default:
			return null;
		}
	}
}

package com.challenge.food.infrastructure.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.challenge.food.core.email.EmailProperties;
import com.challenge.food.domain.service.EmailService;
@Service
public class SmtpEmailService implements EmailService {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private EmailProperties properties;
	
	@Override
	public void enviar(Email email) {
		try {
		MimeMessage mimeMessage= sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(properties.getRemetente());
		helper.setTo(email.getRemetentes().toArray(new String[0]));
		helper.setSubject(email.getTitulo());
	
			helper.setText(email.getCorpo(), true);
			sender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Nao foi possivel enviar o email", e);
		}
		
		
	}

}

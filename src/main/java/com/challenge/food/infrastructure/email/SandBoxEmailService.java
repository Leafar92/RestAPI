package com.challenge.food.infrastructure.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.challenge.food.core.email.EmailProperties;
import com.challenge.food.domain.service.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
public class SandBoxEmailService implements EmailService {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private EmailProperties properties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void enviar(Email email) {
		try {
		String corpo = getCorpoEmail(email);	
			
		MimeMessage mimeMessage= sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(properties.getRemetente());
		helper.setTo(properties.getDestinatario());
		helper.setSubject(email.getTitulo());
	
			helper.setText(corpo, true);
			sender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Nao foi possivel enviar o email", e);
		}
		
		
	}
	
	protected String getCorpoEmail(Email email) {
		try {
			Template template = freemarkerConfig.getTemplate(email.getCorpo());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Nao foi possivel gearar o corpo do email", e);
		}

	}

}

package com.challenge.food.infrastructure.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvioEmailFake extends SmtpEmailService {
	
	

	@Override
	public void enviar(Email email) {
		try {
			String corpo = getCorpoEmail(email);

		log.info("Gerando email fake" + corpo);
		} catch (Exception e) {
			throw new EmailException("Nao foi possivel enviar o email", e);
		}

	}


}

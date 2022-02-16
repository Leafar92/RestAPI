package com.challenge.food.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

public interface EmailService {

	void enviar(Email email);
	
	@Builder
	@Getter
	class Email{
		private Set<String> remetentes;
		private String titulo;
		private String corpo;
	}
}

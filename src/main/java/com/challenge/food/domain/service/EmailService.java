package com.challenge.food.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EmailService {

	void enviar(Email email);
	
	@Builder
	@Getter
	class Email{
		
		@Singular
		private Set<String> destinatarios;
		
		@NonNull
		private String titulo;
		
		@NonNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String , Object> variaveis;
	}
}

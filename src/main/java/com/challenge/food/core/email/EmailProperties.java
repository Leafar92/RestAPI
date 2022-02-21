package com.challenge.food.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Component
@ConfigurationProperties("food.email")
@Getter
@Setter
public class EmailProperties {

	@NotNull
	private String remetente;
	
	private Implementacao impl = Implementacao.SANDBOX;
	
	private String destinatario;
	
	
	public enum Implementacao{
		FAKE,SMTP,SANDBOX
	}
}

package com.challenge.food.api.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdate {

	@NotBlank
	private String nome;

	@NotBlank
	@Email
	private String email;
	
}

package com.challenge.food.api.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSenha {

	@NotBlank
	private String senha;
	
	@NotBlank
	private String senhaConfirmacao;
	

}

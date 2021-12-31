package com.challenge.food.api.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;

}

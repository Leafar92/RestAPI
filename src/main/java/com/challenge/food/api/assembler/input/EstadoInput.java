package com.challenge.food.api.assembler.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInput {
	@NotBlank
	private String nome;

}

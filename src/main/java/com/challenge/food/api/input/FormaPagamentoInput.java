package com.challenge.food.api.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {
	@NotBlank
	private String nome;

}
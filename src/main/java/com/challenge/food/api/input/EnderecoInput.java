package com.challenge.food.api.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	private String rua;
	private Long numero;
	private Long cep;
	private CidadeIDInput cidade;
}

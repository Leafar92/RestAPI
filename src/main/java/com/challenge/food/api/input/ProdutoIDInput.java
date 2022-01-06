package com.challenge.food.api.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoIDInput {

	@NotNull
	private Long id;

}

package com.challenge.food.api.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIDInput {
	
	@NotNull
	private Long id;

}

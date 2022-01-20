package com.challenge.food.domain.model.input;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiariaInput {

	private Long restauranteId;
	
	private OffsetDateTime dataCriacaoInicio;
	
	private OffsetDateTime dataCriacaoFim;
}

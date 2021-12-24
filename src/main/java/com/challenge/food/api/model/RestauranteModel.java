package com.challenge.food.api.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestauranteModel {
	private Long id;
	private String nome;
	
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;

}

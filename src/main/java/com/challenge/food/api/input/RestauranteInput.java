package com.challenge.food.api.input;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.challenge.food.domain.model.Endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {
	@NotBlank
	private String nome;
	
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	private CozinhaIdInput cozinha;


	@Embedded
	private Endereco endereco;
}

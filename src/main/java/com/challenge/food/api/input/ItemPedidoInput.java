package com.challenge.food.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
	@NotBlank
	private String observacao;
	
	@NotNull
	private Integer quantidade;
	
	@NotNull
	private Long produtoID;
}

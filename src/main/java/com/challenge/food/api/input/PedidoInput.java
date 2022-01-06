package com.challenge.food.api.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {

	@NotNull
	@Valid
	private FormaPagamentoIDInput formaPagamento;
	
	@NotNull
	@Valid
	private RestauranteIDInput restaurante;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoInput> itens;
	
	@Valid
	@NotNull
	private EnderecoInput enderecoEntrega;
	
}

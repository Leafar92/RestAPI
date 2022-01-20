package com.challenge.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String codigo) {
		super(String.format("O pedido de codigo %s nao foi encontrado", codigo));
	}

}

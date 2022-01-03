package com.challenge.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String message) {
		super(message);
	}
	
	public PedidoNaoEncontradoException(Long id) {
		this(String.format("O pedido de Id %d nao foi encontrado", id));
	}

	
}

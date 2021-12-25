package com.challenge.food.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoEncontradaException(String message) {
		super(message);
	}
	
	public FormaPagamentoNaoEncontradaException(Long id) {
		this(String.format("A forma de pagamento de Id %d nao foi encontrada", id));
	}

	
}

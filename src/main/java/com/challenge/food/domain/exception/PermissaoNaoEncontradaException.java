package com.challenge.food.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public PermissaoNaoEncontradaException(String message) {
		super(message);
	}
	
	public PermissaoNaoEncontradaException(Long id) {
		this(String.format("A permissao de Id %d nao foi encontrada", id));
	}

	
}

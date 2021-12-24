package com.challenge.food.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaException(String message) {
		super(message);
	}
	
	public CidadeNaoEncontradaException(Long id) {
		this(String.format("A cidade de Id %d nao foi encontrada", id));
	}

	
}

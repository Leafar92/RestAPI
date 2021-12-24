package com.challenge.food.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradoException(String message) {
		super(message);
	}
	
	public EstadoNaoEncontradoException(Long id) {
		this(String.format("O estado de Id %d nao foi encontrado", id));
	}

	
}

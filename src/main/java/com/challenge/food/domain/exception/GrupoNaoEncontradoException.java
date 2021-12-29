package com.challenge.food.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String message) {
		super(message);
	}
	
	public GrupoNaoEncontradoException(Long id) {
		this(String.format("O grupo de Id %d nao foi encontrado", id));
	}

	
}

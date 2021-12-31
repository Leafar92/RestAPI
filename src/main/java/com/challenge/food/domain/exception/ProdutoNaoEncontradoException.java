package com.challenge.food.domain.exception;

public class ProdutoNaoEncontradoException  extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException(String message) {
		super(message);
	}
	
	public ProdutoNaoEncontradoException(Long id) {
		this(String.format("O produto de Id %d nao foi encontrado", id));
	}
}

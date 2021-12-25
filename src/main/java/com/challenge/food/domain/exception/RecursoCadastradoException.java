package com.challenge.food.domain.exception;

public class RecursoCadastradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	public RecursoCadastradoException(String nome) {
		super(String.format("O recurso  %s ja existe", nome));
	}

	
}

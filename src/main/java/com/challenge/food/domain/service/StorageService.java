package com.challenge.food.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface StorageService {

	void armazenar(NovaFoto novaFoto);
	
	void deletar(String nomeArquivo);
	
	InputStream recuperar(String nomeArquivo);
	
	default void substituir(String nomeArquivo, NovaFoto novaFoto) {
		armazenar(novaFoto);
		
		if(nomeArquivo!=null) {
			deletar(nomeArquivo);
		}
	}
	
	default String createNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

	@Getter
	@Builder
	class NovaFoto {
		private InputStream inputStream;
		private String nomeArquivo;
	}
}

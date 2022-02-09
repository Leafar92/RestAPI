package com.challenge.food.domain.service;

import java.io.InputStream;

import com.challenge.food.domain.model.FotoProduto;

public interface FotoProdutoRepository {

	FotoProduto save(FotoProduto fotoProduto, InputStream inputStream);
	
	void delete(FotoProduto fotoProduto);
	
	FotoProduto getFoto(Long idRestaurante, Long idProduto);
	
	void delete(Long idRestaurante, Long idProduto);
}

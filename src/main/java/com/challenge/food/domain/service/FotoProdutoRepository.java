package com.challenge.food.domain.service;

import com.challenge.food.domain.model.FotoProduto;

public interface FotoProdutoRepository {

	FotoProduto save(FotoProduto fotoProduto);
	
	void delete(FotoProduto fotoProduto);
}

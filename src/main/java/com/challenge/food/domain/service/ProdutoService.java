package com.challenge.food.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Transactional
	public void update(Produto produto) {
		produtoRepository.save(produto);
	}

	@Transactional
	public void delete(Produto produto) {
			produtoRepository.delete(produto);
			produtoRepository.flush();
	}
	
	public List<Produto> findByRestaurante(Restaurante restaurante){
		return produtoRepository.findByRestaurante(restaurante);
	}
	
	public Produto findByRestauranteAndId(Long idRestaurante, Long id) {
		return produtoRepository.findByIDAndRestaurante(idRestaurante, id)
				.orElseThrow(()-> 
				new NegocioException(String.format("O produto de id %d nao esta vinculado com o restaurante de id %d"
						, id, idRestaurante)));
	}
	
}

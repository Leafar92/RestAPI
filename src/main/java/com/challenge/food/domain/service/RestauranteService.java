package com.challenge.food.domain.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.exception.RecursoNaoEncontradoException;
import com.challenge.food.domain.model.Cozinha;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	public Restaurante findById(Long id) {
		return  restauranteRepository.findById(id).orElseThrow(() ->
				new EntidadeNaoEncontradaException(String.format("O restaurante de Id %d nao foi encontrado", id)));
	
	}
	
	@Transactional
	public Restaurante save(Restaurante restaurante) {
		Cozinha cozinha = cozinhaService.findById(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public Restaurante update(Long idRestaurante, Restaurante restaurante) {
		restaurante = restauranteRepository.findById(idRestaurante)
				.orElseThrow(()-> new RecursoNaoEncontradoException(
						String.format("O restaurante de id %d nao foi encontrado",idRestaurante)));
		
		return save(restaurante);
	}
	
	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.ativar();
	}
	
	@Transactional
	public void desativar(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.desativar();
	}
}

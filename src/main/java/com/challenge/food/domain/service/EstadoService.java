package com.challenge.food.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.EstadoNaoEncontradoException;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRespository estadoRepository;

	public Estado findById(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Transactional
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void update(Long idEstado, Estado estado) {
		findById(idEstado);

		save(estado);
	}

	@Transactional
	public void delete(Long idEstado) {
		Estado estado = findById(idEstado);

		estadoRepository.delete(estado);
		
		estadoRepository.flush();

	}

}

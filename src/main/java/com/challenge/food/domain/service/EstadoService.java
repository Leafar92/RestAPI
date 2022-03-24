package com.challenge.food.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.EstadoNaoEncontradoException;
import com.challenge.food.domain.exception.RecursoCadastradoException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstadoService {

	private final EstadoRespository estadoRepository;

	public Estado findByIdOrThrowException(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Transactional
	public Estado save(Estado estado) {
		verifyByName(estado.getNome());
		return estadoRepository.save(estado);
	}

	@Transactional
	public void update(Estado estado) {
		estadoRepository.save(estado);
	}

	@Transactional
	public void delete(Long idEstado) {
		Estado estado = findByIdOrThrowException(idEstado);

		try {
			estadoRepository.delete(estado);
			estadoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new RecursoEmUsoException(String.format("O estado de id %d nao pode ser excluido", idEstado));
		}

	}
	
	public void verifyByName(String nome) {
		Optional<Estado> containsByName = estadoRepository.findByName(nome);
		if(containsByName.isPresent()){
			throw new RecursoCadastradoException(nome);
		}
	}

}

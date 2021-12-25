package com.challenge.food.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.CidadeNaoEncontradaException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.challenge.food.domain.model.Cidade;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.CidadeRepository;

@Service
public class CidadeService {


	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	public Cidade findById(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));

	}
	
	@Transactional
	public Cidade save(Cidade cidade) {
			Estado estado = estadoService.findByIdOrThrowException(cidade.getEstado().getId());	
			cidade.setEstado(estado);
			return cidadeRepository.save(cidade);
		
	}
	
	@Transactional
	public void update (Cidade cidade) {
		save(cidade);
	}

	@Transactional
	public void delete(Long idCidade) {
		Cidade cidade = findById(idCidade);
		try{
			cidadeRepository.delete(cidade);
			cidadeRepository.flush();
		}catch(DataIntegrityViolationException e) {
			throw new RecursoEmUsoException(String.format("A cidade %d esta sendo utilizada", cidade.getId()));
		}
	
	}
	
}

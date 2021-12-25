package com.challenge.food.domain.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.exception.RecursoCadastradoException;
import com.challenge.food.domain.model.Cozinha;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.CozinhaRespository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRespository cozinhaRespository;

	public Cozinha findById(Long id) {
		return cozinhaRespository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("A cozinha de Id %d nao foi encontrada", id)));

	}

	@Transactional
	public Cozinha save(Cozinha cozinha) {
		return cozinhaRespository.save(cozinha);
	}

	@Transactional
	public void delete(Long id) {
		cozinhaRespository.delete(findById(id));
		cozinhaRespository.flush();
	}
	
	public void verifyByName(String nome) {
		Optional<Cozinha> containsByName = cozinhaRespository.findByName(nome);
		if(containsByName.isPresent()){
			throw new RecursoCadastradoException(nome);
		}
	}


}

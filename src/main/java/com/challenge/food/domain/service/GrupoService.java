package com.challenge.food.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.GrupoNaoEncontradoException;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	public Grupo findByIdOrThrowException(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	@Transactional
	public Grupo save(Grupo grupo) {
//		/verifyByName(grupo.getNome());
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void update(Grupo formaPagamento) {
		grupoRepository.save(formaPagamento);
	}

	@Transactional
	public void delete(Long idGrupo) {
		Grupo formaPagamento = findByIdOrThrowException(idGrupo);
			grupoRepository.delete(formaPagamento);
			grupoRepository.flush();
	}
	
//	public void verifyByName(String nome) {
//		Optional<Grupo> containsByName = grupoRepository.findByName(nome);
//		if(containsByName.isPresent()){
//			throw new RecursoCadastradoException(nome);
//		}
	//}

}

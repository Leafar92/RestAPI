package com.challenge.food.domain.service;



import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.exception.GrupoNaoEncontradoException;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.model.Permissao;
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
	public void update(Grupo grupo) {
		grupoRepository.save(grupo);
	}

	@Transactional
	public void delete(Long idGrupo) {
		Grupo formaPagamento = findByIdOrThrowException(idGrupo);
			grupoRepository.delete(formaPagamento);
			grupoRepository.flush();
	}
	
	@Transactional
	public void associarPermissao(Grupo grupo, Permissao permissao) {
		grupo.associarPermissao(permissao);

	}
	
	@Transactional
	public void desassociarPermissao(Grupo grupo, Permissao permissao) {
		
		Permissao found = getPermissaoOrThrosException(grupo, permissao);
		
		grupo.desassociarPermissao(found);
		
	}

	public Permissao getPermissaoOrThrosException(Grupo grupo, Permissao permissao) {
	 Optional<Permissao> found = grupo.getPermissoes().stream().filter(p -> p.equals(permissao)).findFirst();
		if(grupo.getPermissoes().isEmpty() || found.isEmpty()) {
			throw new NegocioException(String.format("O grupo de id %d nao possui a permissao %d", grupo.getId(), permissao.getId()));
		}
		
		return found.get();
	}
	
}

package com.challenge.food.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.PermissaoNaoEncontradaException;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.model.Permissao;
import com.challenge.food.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRespository;

	public Permissao findByIdOrThrowException(Long id) {
		return permissaoRespository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}

	@Transactional
	public Permissao save(Permissao permissao) {
		return permissaoRespository.save(permissao);
	}

	@Transactional
	public void update(Permissao permissao) {
		permissaoRespository.save(permissao);
	}

	@Transactional
	public void delete(Long idPermissao) {
		Permissao permissao = findByIdOrThrowException(idPermissao);
			permissaoRespository.delete(permissao);
			permissaoRespository.flush();
	}
	public Permissao getPermissaoOrThrosException(Grupo grupo, Permissao permissao) {
		 Optional<Permissao> found = grupo.getPermissoes().stream().filter(p -> p.equals(permissao)).findFirst();
			if(grupo.getPermissoes().isEmpty() || found.isEmpty()) {
				throw new NegocioException(String.format("O grupo de id %d nao possui a permissao %d", grupo.getId(), permissao.getId()));
			}
			
			return found.get();
		}
		
	
}

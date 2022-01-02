package com.challenge.food.domain.service;



import java.util.List;
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
	
	@Autowired
	private PermissaoService permissaoService;

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
		
		Permissao found = permissaoService.getPermissaoOrThrosException(grupo, permissao);
		
		grupo.desassociarPermissao(found);
		
	}

	public Grupo getGrupoOrThrowsException(Set<Grupo> grupos, Long idGrupo, Long idUsuario) {
		Grupo grupo = findByIdOrThrowException(idGrupo);
		Optional<Grupo> found = grupos.stream().filter(g -> g.equals(grupo)).findFirst();
		if (grupos.isEmpty() || found.isEmpty()) {
			throw new NegocioException(
					String.format("O Usuario de id %d nao possui o grupo de id %d", idUsuario, grupo.getId()));
		}

		return found.get();
	}

}

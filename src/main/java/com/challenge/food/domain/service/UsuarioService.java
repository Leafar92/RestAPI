package com.challenge.food.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.challenge.food.domain.exception.UsuarioNaoEncontradoException;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.model.Permissao;
import com.challenge.food.domain.model.Usuario;
import com.challenge.food.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public Usuario findByIdOrThrowException(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	@Transactional
	public Usuario save(Usuario user) {
		entityManager.detach(user);
		Optional<Usuario> found = usuarioRepository.findByEmail(user.getEmail());
		
		if(found.isPresent() && !found.get().equals(user)) {
			throw new RecursoEmUsoException(String.format("O email %s ja esta sendo utilizado", user.getEmail()));
		}
		return usuarioRepository.save(user);
	}

	@Transactional
	public void update(Usuario user) {
		save(user);
	}

	@Transactional
	public void delete(Long idUser) {
		Usuario user = findByIdOrThrowException(idUser);
			usuarioRepository.delete(user);
			usuarioRepository.flush();
	}
	
	@Transactional
	public void associarGrupo(Grupo grupo, Usuario usuario) {
		usuario.associarGrupo(grupo);

	}
	
	@Transactional
	public void desassociarGrupo(Grupo grupo, Usuario usuario) {
		usuario.desassociarGrupo(grupo);

	}
	
	public void validarSenha(String senha, String confirmacao) {
		if(!senha.equals(confirmacao)) {
			throw new NegocioException("As senhas nao sao iguais");
		}
	}
	
}

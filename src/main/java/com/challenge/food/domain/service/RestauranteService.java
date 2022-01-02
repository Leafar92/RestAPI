package com.challenge.food.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.RecursoNaoEncontradoException;
import com.challenge.food.domain.model.Cozinha;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.model.Usuario;
import com.challenge.food.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private UsuarioService usuarioService;

	public Restaurante findById(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("O restaurante de Id %d nao foi encontrado", id)));

	}

	@Transactional
	public Restaurante save(Restaurante restaurante) {
		Cozinha cozinha = cozinhaService.findById(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public Restaurante update(Long idRestaurante, Restaurante restaurante) {
		restaurante = restauranteRepository.findById(idRestaurante).orElseThrow(() -> new RecursoNaoEncontradoException(
				String.format("O restaurante de id %d nao foi encontrado", idRestaurante)));

		return save(restaurante);
	}

	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.ativar();
	}

	@Transactional
	public void ativar(List<Long> idsRestaurantes) {
		idsRestaurantes.forEach(this::ativar);
	}

	@Transactional
	public void desativar(List<Long> idsRestaurantes) {
		idsRestaurantes.forEach(this::desativar);
	}

	@Transactional
	public void desativar(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.desativar();
	}

	@Transactional
	public void abrir(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.abrir();
	}

	@Transactional
	public void fechar(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.fechar();
	}

	@Transactional
	public void associarResponsavel(Long idRestaurante, Long idUsuario) {
		Restaurante restaurante = findById(idRestaurante);
		Usuario usuario = usuarioService.findByIdOrThrowException(idUsuario);

		restaurante.associarResponsavel(usuario);
	}

	@Transactional
	public void desassociarResponsavel(Long idRestaurante, Long idUsuario) {
		Restaurante restaurante = findById(idRestaurante);
		Usuario usuario = getUsuarioOrThrowsException(restaurante.getResponsaveis(), idUsuario, idRestaurante);
		restaurante.desassociarResponsavel(usuario);
	}

	public Usuario getUsuarioOrThrowsException(Set<Usuario> responsaveis, Long idUsuario, Long idRestaurante) {
		Usuario usuario = usuarioService.findByIdOrThrowException(idUsuario);
		Optional<Usuario> found = responsaveis.stream().filter(r -> r.equals(usuario)).findFirst();

		if (responsaveis.isEmpty() || found.isEmpty()) {
			throw new NegocioException(String.format("O usuario de id %d nao esta associado ao restaurante de id %d",
					idUsuario, idRestaurante));
		}

		return found.get();
	}
}

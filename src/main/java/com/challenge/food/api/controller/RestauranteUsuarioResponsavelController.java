package com.challenge.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.assembler.UsuarioModelAssembler;
import com.challenge.food.api.model.UsuarioModel;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.model.Usuario;
import com.challenge.food.domain.service.RestauranteService;

@RestController
@RequestMapping("restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@GetMapping
	public List<UsuarioModel> listarResponsaveis(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.findById(idRestaurante);
		return usuarioModelAssembler.toListModel(restaurante.getResponsaveis());
	}

	@PutMapping("/{idUsuario}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void associarResponsavel(@PathVariable Long idUsuario, @PathVariable Long idRestaurante) {
		restauranteService.associarResponsavel(idRestaurante, idUsuario);
	}

	@GetMapping("/{idUsuario}")
	public UsuarioModel listarResponsavelPorID(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
		Restaurante restaurante = restauranteService.findById(idRestaurante);
		Usuario usuario = restauranteService.getUsuarioOrThrowsException(restaurante.getResponsaveis(), idUsuario,
				idRestaurante);
		return usuarioModelAssembler.toModel(usuario);
	}

	@DeleteMapping("/{idUsuario}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateProduto(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
		restauranteService.desassociarResponsavel(idRestaurante, idUsuario);

	}

}

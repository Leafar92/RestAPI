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

import com.challenge.food.api.assembler.GrupoModelAssembler;
import com.challenge.food.api.model.GrupoModel;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.model.Usuario;
import com.challenge.food.domain.service.GrupoService;
import com.challenge.food.domain.service.UsuarioService;

@RestController
@RequestMapping("usuarios/{idUsuario}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public List<GrupoModel> listarGrupos(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioService.findByIdOrThrowException(idUsuario);

		return grupoModelAssembler.toListModel(usuario.getGrupos());
	}

	@GetMapping("/{idGrupo}")
	public GrupoModel listarPermissaoPorID(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		Usuario usuario = usuarioService.findByIdOrThrowException(idUsuario);

		Grupo found = grupoService.getGrupoOrThrowsException(usuario.getGrupos(), idGrupo, idUsuario);

		return grupoModelAssembler.toModel(found);
	}

	@PutMapping("/{idGrupo}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);

		Usuario usuario = usuarioService.findByIdOrThrowException(idUsuario);

		usuarioService.associarGrupo(grupo, usuario);

	}

	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);

		Usuario usuario = usuarioService.findByIdOrThrowException(idUsuario);

		usuarioService.desassociarGrupo(grupo, usuario);
	}

}

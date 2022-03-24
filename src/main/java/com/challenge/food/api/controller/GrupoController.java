package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.ResourceAPI;
import com.challenge.food.api.assembler.GrupoInputDisassembler;
import com.challenge.food.api.assembler.GrupoModelAssembler;
import com.challenge.food.api.input.GrupoInput;
import com.challenge.food.api.model.GrupoModel;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.repository.GrupoRepository;
import com.challenge.food.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	public List<GrupoModel> listar() {
		return grupoModelAssembler.toListModel(grupoRepository.findAll());
	}

	@GetMapping("/{idGrupo}")
	public GrupoModel findById(@PathVariable Long idGrupo) {
		return grupoModelAssembler.toModel(grupoService.findByIdOrThrowException(idGrupo));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public GrupoModel save(@RequestBody @Valid GrupoInput input) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(input);
		ResourceAPI.addUriInResponseHeader(grupo.getId());
		return grupoModelAssembler.toModel(grupoService.save(grupo));
	}

	@PutMapping("/{idGrupo}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idGrupo, @RequestBody GrupoInput input) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);

		/// grupoService.verifyByName(input.getNome());

		grupoInputDisassembler.copyToDomainObject(grupo, input);

		grupoService.update(grupo);

	}

	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idGrupo) {
		try {
			grupoService.delete(idGrupo);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(String.format("O grupo de ID %d nao pode ser excluido", idGrupo));
		}
	}

}

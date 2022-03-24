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
import com.challenge.food.api.assembler.PermissaoInputDisassembler;
import com.challenge.food.api.assembler.PermissaoModelAssembler;
import com.challenge.food.api.input.PermissaoInput;
import com.challenge.food.api.model.PermissaoModel;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Permissao;
import com.challenge.food.domain.repository.PermissaoRepository;
import com.challenge.food.domain.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	private PermissaoInputDisassembler permissaoInputDisassembler;

	@GetMapping
	public List<PermissaoModel> listar() {
		return permissaoModelAssembler.toListModel(permissaoRepository.findAll());
	}

	@GetMapping("/{idPermissao}")
	public PermissaoModel findById(@PathVariable Long idPermissao) {
		return permissaoModelAssembler.toModel(permissaoService.findByIdOrThrowException(idPermissao));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PermissaoModel save(@RequestBody @Valid PermissaoInput input) {
		Permissao permissao = permissaoInputDisassembler.toDomainObject(input);
		ResourceAPI.addUriInResponseHeader(permissao.getId());
		return permissaoModelAssembler.toModel(permissaoService.save(permissao));
	}

	@PutMapping("/{idPermissao}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idPermissao, @RequestBody PermissaoInput input) {
		Permissao permissao = permissaoService.findByIdOrThrowException(idPermissao);


		permissaoInputDisassembler.copyToDomainObject(permissao, input);

		permissaoService.update(permissao);

	}

	@DeleteMapping("/{idPermissao}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idPermissao) {
		try {
			permissaoService.delete(idPermissao);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(
					String.format("A permissao de ID %d nao pode ser excluida", idPermissao));
		}
	}

}

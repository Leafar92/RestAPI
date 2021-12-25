package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.challenge.food.api.assembler.CidadeInputDisassembler;
import com.challenge.food.api.assembler.CidadeModelAssembler;
import com.challenge.food.api.input.CidadeInput;
import com.challenge.food.api.model.CidadeModel;
import com.challenge.food.domain.exception.EstadoNaoEncontradoException;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.challenge.food.domain.model.Cidade;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.CidadeRepository;
import com.challenge.food.domain.service.CidadeService;
import com.challenge.food.domain.service.EstadoService;

@RestController
@RequestMapping("cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toListModel(cidadeRepository.findAll());
	}

	@GetMapping("/{cidadeID}")
	public CidadeModel findById(@PathVariable Long cidadeID) {
		return cidadeModelAssembler.toModel(cidadeService.findById(cidadeID));
	}

	@PutMapping("/{cidadeID}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long cidadeID, @RequestBody @Valid CidadeInput input) {
		try {

			Cidade cidadeAtual = cidadeService.findById(cidadeID);

			cidadeInputDisassembler.copyToDomainObject(cidadeAtual, input);

			cidadeService.update(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(String.format("O estado de id %d nao existe", input.getEstado().getId()));
		}
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CidadeModel save(@RequestBody @Valid CidadeInput input) {

		try {
			Estado estado = estadoService.findByIdOrThrowException(input.getEstado().getId());

			Cidade cidade = cidadeInputDisassembler.toDomainObject(input);

			cidade.setEstado(estado);

			return cidadeModelAssembler.toModel(cidadeService.save(cidade));

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(String.format("A cidade de ID %d nao existe", input.getEstado().getId()));
		}
	}

	@DeleteMapping("/{cidadeID}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cidadeID) {
		try {
			cidadeService.delete(cidadeID);
		} catch (ConstraintViolationException e) {
			throw new RecursoEmUsoException(String.format("A cidade de ID %d esta em uso", cidadeID));
		}
	}

}

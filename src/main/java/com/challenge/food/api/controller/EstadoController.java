package com.challenge.food.api.controller;

import java.util.List;

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

import com.challenge.food.api.assembler.EstadoInputDisassembler;
import com.challenge.food.api.assembler.EstadoModelAssembler;
import com.challenge.food.api.assembler.input.EstadoInput;
import com.challenge.food.api.model.EstadoModel;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;
import com.challenge.food.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private EstadoRespository estadoRepository;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoModel> listar() {
		return estadoModelAssembler.toListModel(estadoRepository.findAll());
	}

	@GetMapping("/{idEstado}")
	public EstadoModel findById(@PathVariable Long idEstado) {
		return estadoModelAssembler.toModel(estadoService.findByIdOrThrowException(idEstado));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public EstadoModel save(@RequestBody @Valid EstadoInput input) {
		Estado estado = estadoInputDisassembler.toDomainObject(input);
		return estadoModelAssembler.toModel(estadoService.save(estado));
	}

	@PutMapping("/{idEstado}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idEstado, @RequestBody EstadoInput input) {
		Estado estadoBD = estadoService.findByIdOrThrowException(idEstado);

		estadoService.verifyByName(input.getNome());
		
		estadoInputDisassembler.copyToDomainObject(estadoBD, input);

		estadoService.update(estadoBD);

	}

	@DeleteMapping("/{idEstado}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idEstado) {
		estadoService.delete(idEstado);
	}

}

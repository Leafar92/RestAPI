package com.challenge.food.api.controller;

import java.util.List;

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

import com.challenge.food.api.assembler.RestauranteInputDisassembler;
import com.challenge.food.api.assembler.RestauranteModelAssembler;
import com.challenge.food.api.input.RestauranteInput;
import com.challenge.food.api.model.RestauranteModel;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.RecursoNaoEncontradoException;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.repository.RestauranteRepository;
import com.challenge.food.domain.service.RestauranteService;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteService service;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssemble;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssemble.toListModel(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteID}")
	public RestauranteModel findById(@PathVariable Long restauranteID) {
		return restauranteModelAssemble.toModel(service.findById(restauranteID));
	}


	@PutMapping("/{idRestaurante}")
	public RestauranteModel update(@PathVariable Long idRestaurante, @RequestBody RestauranteInput input) {

		Restaurante restauranteAtual = service.findById(idRestaurante);

		restauranteInputDisassembler.copyToDomainObject(restauranteAtual, input);
		restauranteAtual = service.update(idRestaurante, restauranteAtual);
		return restauranteModelAssemble.toModel(restauranteAtual);

	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public RestauranteModel save(@RequestBody RestauranteInput input) {
		Restaurante saved = service.save(restauranteInputDisassembler.toDomainObject(input));

		return restauranteModelAssemble.toModel(saved);

	}
	
	@PutMapping("/{idRestaurante}/ativacao")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long idRestaurante) {
		service.ativar(idRestaurante);
	}
	
	@PutMapping("/ativacao-restaurantes")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativar(@RequestBody List<Long> idsRestaurante) {
		try {
			service.ativar(idsRestaurante);
		}catch(RecursoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/desativacao-restaurantes")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void desativar(@RequestBody  List<Long> idsRestaurante) {
		service.desativar(idsRestaurante);
	}
	
	@PutMapping("/{idRestaurante}/abertura")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long idRestaurante) {
		service.abrir(idRestaurante);
	}
	
	@DeleteMapping("/{idRestaurante}/fechamento")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long idRestaurante) {
		service.fechar(idRestaurante);
	}

}

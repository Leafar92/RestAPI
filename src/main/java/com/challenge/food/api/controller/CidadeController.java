package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.challenge.food.domain.exception.EstadoNaoEncontradoException;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Cidade;
import com.challenge.food.domain.repository.CidadeRepository;
import com.challenge.food.domain.service.CidadeService;

@RestController
@RequestMapping("cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeID}")
	public Cidade findById(@PathVariable Long cidadeID) {
		return cidadeService.findById(cidadeID);
	}

	@PutMapping("/{cidadeID}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long cidadeID, @RequestBody Cidade cidade) {
		try {

			Cidade cidadeAtual = cidadeService.findById(cidadeID);

			BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			cidadeService.update(cidadeID, cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(String.format("O estado de id %d nao existe", cidade.getEstado().getId()));
		}
	}

	@PostMapping
	public Cidade save(@RequestBody @Valid Cidade cidade) {

		return cidadeService.save(cidade);
	}

	@DeleteMapping("/{cidadeID}")
	public void delete(@PathVariable Long cidadeID) {

		cidadeService.delete(cidadeID);
	}

}

package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.challenge.food.api.assembler.CozinhaInputDisassembler;
import com.challenge.food.api.assembler.CozinhaModelAssembler;
import com.challenge.food.api.input.CozinhaInput;
import com.challenge.food.api.model.CozinhaModel;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.challenge.food.domain.model.Cozinha;
import com.challenge.food.domain.repository.CozinhaRespository;
import com.challenge.food.domain.service.CozinhaService;


@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRespository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputAssembler;
	
	@GetMapping
	public Page<CozinhaModel> listar(Pageable pagebable){
		Page<Cozinha> cozinhas = cozinhaRepository.findAll(pagebable);
		
		 List<CozinhaModel> cozinhasModel = cozinhaAssembler.toListModel(cozinhas.getContent());
		 
		 Page<CozinhaModel> cozinhasPage = new PageImpl<>(cozinhasModel, pagebable, cozinhas.getTotalElements());
		 
		 return cozinhasPage;
	}
	
	@GetMapping("/{idCozinha}")
	public CozinhaModel listarByID(@PathVariable Long idCozinha){
		return cozinhaAssembler.toModel(cozinhaService.findById(idCozinha));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CozinhaModel save(@RequestBody @Valid CozinhaInput input) {
		Cozinha cozinha = cozinhaInputAssembler.toDomainObject(input);
		
		cozinhaService.verifyByName(input.getNome());
		
		return cozinhaAssembler.toModel(cozinhaService.save(cozinha));
	}
	
	@PutMapping("/{idCozinha}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idCozinha, @RequestBody @Valid CozinhaInput input){
		Cozinha cozinha = cozinhaService.findById(idCozinha);
		
		cozinhaService.verifyByName(input.getNome());
		
		cozinhaInputAssembler.copyToDomainObject(cozinha, input);
		
		cozinhaService.save(cozinha);
	}
	
	
	@DeleteMapping("/{idCozinha}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idCozinha){
		try {
			cozinhaService.delete(idCozinha);
		}catch (DataIntegrityViolationException e) {
			throw new RecursoEmUsoException(String.format("A cozinha de ID %d esta em uso", idCozinha));
		}
		
	}
}

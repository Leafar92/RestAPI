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

import com.challenge.food.api.assembler.FormaPagamentoInputDisassembler;
import com.challenge.food.api.assembler.FormaPagamentoModelAssembler;
import com.challenge.food.api.input.FormaPagamentoInput;
import com.challenge.food.api.model.FormaPagamentoModel;
import com.challenge.food.domain.model.FormaPagamento;
import com.challenge.food.domain.repository.FormaPagamentoRepository;
import com.challenge.food.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService formarPagamentoService;

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public List<FormaPagamentoModel> listar() {
		return formaPagamentoModelAssembler.toListModel(formaPagamentoRepository.findAll());
	}

	@GetMapping("/{idFormaPagamento}")
	public FormaPagamentoModel findById(@PathVariable Long idFormaPagamento) {
		return formaPagamentoModelAssembler.toModel(formarPagamentoService.findByIdOrThrowException(idFormaPagamento));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public FormaPagamentoModel save(@RequestBody @Valid FormaPagamentoInput input) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(input);
		return formaPagamentoModelAssembler.toModel(formarPagamentoService.save(formaPagamento));
	}

	@PutMapping("/{idFormaPagamento}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idFormaPagamento, @RequestBody FormaPagamentoInput input) {
		FormaPagamento formaPagamentoBD = formarPagamentoService.findByIdOrThrowException(idFormaPagamento);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoBD, input);

		formarPagamentoService.update(idFormaPagamento, formaPagamentoBD);

	}

	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idFormaPagamento) {
		formarPagamentoService.delete(idFormaPagamento);
	}

}

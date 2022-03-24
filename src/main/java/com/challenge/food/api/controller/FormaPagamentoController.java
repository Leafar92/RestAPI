package com.challenge.food.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.challenge.food.api.ResourceAPI;
import com.challenge.food.api.assembler.FormaPagamentoInputDisassembler;
import com.challenge.food.api.assembler.FormaPagamentoModelAssembler;
import com.challenge.food.api.input.FormaPagamentoInput;
import com.challenge.food.api.model.FormaPagamentoModel;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.FormaPagamento;
import com.challenge.food.domain.repository.FormaPagamentoRepository;
import com.challenge.food.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
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
	public ResponseEntity<List<FormaPagamentoModel>> listar() {
		
		List<FormaPagamentoModel> listModel = formaPagamentoModelAssembler.toListModel(formaPagamentoRepository.findAll());
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(listModel);
	}

	@GetMapping("/{idFormaPagamento}")
	public ResponseEntity<FormaPagamentoModel> findById(@PathVariable Long idFormaPagamento, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag="0";
		
		OffsetDateTime dataAtualizacaoMaxima = formaPagamentoRepository.getDataAtualizacaoMaxima();
		
		if(dataAtualizacaoMaxima != null) {
			eTag = String.valueOf(dataAtualizacaoMaxima.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		
		
		FormaPagamentoModel model = formaPagamentoModelAssembler
				.toModel(formarPagamentoService.findByIdOrThrowException(idFormaPagamento));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(model);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public FormaPagamentoModel save(@RequestBody @Valid FormaPagamentoInput input) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(input);
		ResourceAPI.addUriInResponseHeader(formaPagamento.getId());
		return formaPagamentoModelAssembler.toModel(formarPagamentoService.save(formaPagamento));
	}

	@PutMapping("/{idFormaPagamento}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idFormaPagamento, @RequestBody FormaPagamentoInput input) {
		FormaPagamento formaPagamentoBD = formarPagamentoService.findByIdOrThrowException(idFormaPagamento);

		formarPagamentoService.verifyByName(input.getNome());

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoBD, input);

		formarPagamentoService.update(formaPagamentoBD);

	}

	@DeleteMapping("/{idFormaPagamento}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idFormaPagamento) {
		try {
			formarPagamentoService.delete(idFormaPagamento);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(
					String.format("A forma de pagamento de ID %d nao pode ser excluida", idFormaPagamento));
		}
	}

}

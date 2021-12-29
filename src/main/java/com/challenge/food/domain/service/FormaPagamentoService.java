package com.challenge.food.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.challenge.food.domain.exception.RecursoCadastradoException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.model.FormaPagamento;
import com.challenge.food.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	public FormaPagamento findByIdOrThrowException(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}

	@Transactional
	public FormaPagamento save(FormaPagamento formaPagamento) {
		verifyByName(formaPagamento.getNome());
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void update(FormaPagamento formaPagamento) {
		formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void delete(Long idFormaPagamento) {
		FormaPagamento formaPagamento = findByIdOrThrowException(idFormaPagamento);
			formaPagamentoRepository.delete(formaPagamento);
			formaPagamentoRepository.flush();
	}
	
	public void verifyByName(String nome) {
		Optional<FormaPagamento> containsByName = formaPagamentoRepository.findByName(nome);
		if(containsByName.isPresent()){
			throw new RecursoCadastradoException(nome);
		}
	}

}

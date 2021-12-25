package com.challenge.food.domain.service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
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
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void update(Long idFormaPagamento, FormaPagamento formaPagamento) {
		findByIdOrThrowException(idFormaPagamento);

		save(formaPagamento);
	}

	@Transactional
	public void delete(Long idFormaPagamento) {
		FormaPagamento formaPagamento = findByIdOrThrowException(idFormaPagamento);

		try {
			formaPagamentoRepository.delete(formaPagamento);
			formaPagamentoRepository.flush();
		} catch (ConstraintViolationException e) {
			throw new RecursoEmUsoException(
					String.format("A forma de pagamento de id %d nao pode ser excluida", idFormaPagamento));
		}
	}

}

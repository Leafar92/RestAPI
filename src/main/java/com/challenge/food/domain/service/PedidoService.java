package com.challenge.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.PedidoNaoEncontradoException;
import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRespository;

	public Pedido findByIdOrThrowException(Long id) {
		return pedidoRespository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
	}

	
	
}

package com.challenge.food.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.model.StatusPedido;
import com.challenge.food.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public void confirmar(String codigo) {
		Pedido pedido = pedidoService.findByCodigoOrThrowException(codigo);

		if (pedido.getStatusPedido() != StatusPedido.CRIADO) {
			throw new NegocioException(String.format("O Pedido %s nao pode ter o estado %s alterado para o estado %s",
					codigo, pedido.getStatusPedido().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
		}

		pedido.confirmar();

		pedidoRepository.save(pedido);
	}

	@Transactional
	public void cancelar(String codigo) {
		Pedido pedido = pedidoService.findByCodigoOrThrowException(codigo);

		if (pedido.getStatusPedido() != StatusPedido.CRIADO) {
			throw new NegocioException(String.format("O Pedido %s nao pode ter o estado %s alterado para o estado %s",
					codigo, pedido.getStatusPedido().getDescricao(), StatusPedido.CANCELADO.getDescricao()));
		}

		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	
	}

	@Transactional
	public void entregar(String codigo) {
		Pedido pedido = pedidoService.findByCodigoOrThrowException(codigo);

		if (pedido.getStatusPedido() != StatusPedido.CONFIRMADO) {
			throw new NegocioException(String.format("O Pedido %s nao pode ter o estado %s alterado para o estado %s",
					codigo, pedido.getStatusPedido().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
		}

		pedido.setStatusPedido(StatusPedido.ENTREGUE);
		pedido.setDataEntrega(OffsetDateTime.now());
	}

}

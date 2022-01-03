package com.challenge.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.assembler.PedidoModelAssembler;
import com.challenge.food.api.model.PedidoModel;
import com.challenge.food.domain.repository.PedidoRepository;
import com.challenge.food.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
//
//	@Autowired
//	private PermissaoInputDisassembler permissaoInputDisassembler;

	@GetMapping
	public List<PedidoModel> listar() {
		return pedidoModelAssembler.toListModel(pedidoRepository.findAll());
	}

	@GetMapping("/{idPedido}")
	public PedidoModel findById(@PathVariable Long idPedido) {
		return pedidoModelAssembler.toModel(pedidoService.findByIdOrThrowException(idPedido));
	}



}

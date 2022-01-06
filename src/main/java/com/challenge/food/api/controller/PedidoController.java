package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.assembler.PedidoInputDisassembler;
import com.challenge.food.api.assembler.PedidoModelAssembler;
import com.challenge.food.api.input.PedidoInput;
import com.challenge.food.api.model.PedidoModel;
import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.model.Usuario;
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
	
	@Autowired
	private PedidoInputDisassembler pedidoInputAssembler;

	@GetMapping
	public List<PedidoModel> listar() {
		return pedidoModelAssembler.toListModel(pedidoRepository.findAll());
	}

	@GetMapping("/{idPedido}")
	public PedidoModel findById(@PathVariable Long idPedido) {
		return pedidoModelAssembler.toModel(pedidoService.findByIdOrThrowException(idPedido));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PedidoModel save(@RequestBody @Valid PedidoInput input) {
		try {
			Pedido pedido = pedidoInputAssembler.toDomainObject(input);
			
			Usuario cliente = new Usuario();
			cliente.setId(1L);
			pedido.setCliente(cliente);
			
			pedido = pedidoService.save(pedido);
			return pedidoModelAssembler.toModel(pedido);
			
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}



}

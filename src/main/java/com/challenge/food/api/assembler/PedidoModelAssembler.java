package com.challenge.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.model.PedidoModel;
import com.challenge.food.domain.model.Pedido;

@Component
public class PedidoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoModel.class);
	}
	
	public List<PedidoModel> toListModel(List<Pedido> pedidos){
		return pedidos.stream().map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}

package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.PedidoInput;
import com.challenge.food.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Pedido toDomainObject(PedidoInput input) {

		return modelMapper.map(input, Pedido.class);
	}
	
	public void copyToDomainObject(Pedido pedido, PedidoInput input) {
		modelMapper.map(input, pedido);
	}

}

package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.EstadoInput;
import com.challenge.food.domain.model.Estado;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Estado toDomainObject(EstadoInput input) {

		return modelMapper.map(input, Estado.class);
	}
	
	public void copyToDomainObject(Estado estado, EstadoInput input) {
		modelMapper.map(input, estado);
	}

}

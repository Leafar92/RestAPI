package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.assembler.input.RestauranteInput;
import com.challenge.food.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput input) {

		return modelMapper.map(input, Restaurante.class);
	}
	
	public void copyToDomainObject(Restaurante restaurante, RestauranteInput input) {
		modelMapper.map(input, restaurante);
	}

}

package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.CozinhaInput;
import com.challenge.food.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cozinha toDomainObject(CozinhaInput input) {

		return modelMapper.map(input, Cozinha.class);
	}
	
	public void copyToDomainObject(Cozinha cozinha, CozinhaInput input) {
		modelMapper.map(input, cozinha);
	}

}

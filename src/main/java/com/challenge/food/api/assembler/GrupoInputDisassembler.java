package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.GrupoInput;
import com.challenge.food.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Grupo toDomainObject(GrupoInput input) {

		return modelMapper.map(input, Grupo.class);
	}
	
	public void copyToDomainObject(Grupo grupo, GrupoInput input) {
		modelMapper.map(input, grupo);
	}

}

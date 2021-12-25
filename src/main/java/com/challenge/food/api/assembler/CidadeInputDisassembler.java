package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.CidadeInput;
import com.challenge.food.domain.model.Cidade;
import com.challenge.food.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cidade toDomainObject(CidadeInput input) {
		return modelMapper.map(input, Cidade.class);
	}
	
	public void copyToDomainObject(Cidade cidade, CidadeInput input) {
		cidade.setEstado(new Estado());
		modelMapper.map(input, cidade);
	}

}

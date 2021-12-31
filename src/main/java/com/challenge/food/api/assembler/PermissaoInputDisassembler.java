package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.PermissaoInput;
import com.challenge.food.domain.model.Permissao;

@Component
public class PermissaoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Permissao toDomainObject(PermissaoInput input) {

		return modelMapper.map(input, Permissao.class);
	}
	
	public void copyToDomainObject(Permissao permissao, PermissaoInput input) {
		modelMapper.map(input, permissao);
	}

}

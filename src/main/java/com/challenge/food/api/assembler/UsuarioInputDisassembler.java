package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.UsuarioInput;
import com.challenge.food.api.input.UsuarioUpdate;
import com.challenge.food.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Usuario toDomainObject(UsuarioInput input) {

		return modelMapper.map(input, Usuario.class);
	}
	
	public void copyToDomainObject(Usuario user, UsuarioUpdate input) {
		modelMapper.map(input, user);
	}

}

package com.challenge.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.model.UsuarioModel;
import com.challenge.food.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModel toModel(Usuario user) {
		return modelMapper.map(user, UsuarioModel.class);
	}
	
	public List<UsuarioModel> toListModel(Collection<Usuario> users){
		return users.stream().map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}

package com.challenge.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.model.GrupoModel;
import com.challenge.food.domain.model.Grupo;

@Component
public class GrupoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public GrupoModel toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoModel.class);
	}
	
	public List<GrupoModel> toListModel(Collection<Grupo> grupos){
		return grupos.stream().map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}

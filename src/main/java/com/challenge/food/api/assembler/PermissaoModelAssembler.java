package com.challenge.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.model.PermissaoModel;
import com.challenge.food.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PermissaoModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class);
	}
	
	public List<PermissaoModel> toListModel(Collection<Permissao> permissoes){
		return permissoes.stream().map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}

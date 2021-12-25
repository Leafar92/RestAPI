package com.challenge.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.model.CidadeModel;
import com.challenge.food.domain.model.Cidade;

@Component
public class CidadeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public CidadeModel toModel(Cidade cidade) {
		return modelMapper.map(cidade, CidadeModel.class);
	}
	
	public List<CidadeModel> toListModel(List<Cidade> cidades){
		return cidades.stream().map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}

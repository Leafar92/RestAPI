package com.challenge.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.model.ProdutoModel;
import com.challenge.food.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}
	
	public List<ProdutoModel> toListModel(List<Produto> produtos){
		return produtos.stream().map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}

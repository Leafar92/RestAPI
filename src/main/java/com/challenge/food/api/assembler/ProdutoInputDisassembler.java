package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.input.ProdutoInput;
import com.challenge.food.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Produto toDomainObject(ProdutoInput input) {

		return modelMapper.map(input, Produto.class);
	}
	
	public void copyToDomainObject(Produto produto, ProdutoInput input) {
		modelMapper.map(input, produto);
	}

}

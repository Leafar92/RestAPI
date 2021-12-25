package com.challenge.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.food.api.assembler.input.FormaPagamentoInput;
import com.challenge.food.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamento toDomainObject(FormaPagamentoInput input) {

		return modelMapper.map(input, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamento formaPagamento, FormaPagamentoInput input) {
		modelMapper.map(input, formaPagamento);
	}

}

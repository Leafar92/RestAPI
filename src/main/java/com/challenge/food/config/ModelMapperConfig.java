package com.challenge.food.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.challenge.food.api.input.ItemPedidoInput;
import com.challenge.food.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper createInstance() {
		var mapper = new ModelMapper();
		
		mapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		.addMappings(m -> m.skip(ItemPedido::setId));
		
		return mapper;
	}
}

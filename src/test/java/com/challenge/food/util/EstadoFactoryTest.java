package com.challenge.food.util;

import com.challenge.food.api.input.EstadoInput;
import com.challenge.food.api.model.EstadoModel;
import com.challenge.food.domain.model.Estado;

public class EstadoFactoryTest {

	public static Estado createValidEstado() {
		return new Estado(1L, "Bahia");
	}
	
	public static Estado createEstadoToBeSaved() {
		return Estado.builder().nome("Bahia").build();
	}
	
	public static Estado createEstadoUpdated() {
		return Estado.builder()
				.id(1L)
				.nome("SP").build();
	}
	
	public static EstadoInput createEstadoInput() {
		EstadoInput estadoInput = new EstadoInput();
		
		estadoInput.setNome("Bahia");
		
		return estadoInput;
	}
	
	public static EstadoModel createValidEstadoModel() {
		return EstadoModel.builder()
				.id(1L)
				.nome("Bahia").build();
	}
}

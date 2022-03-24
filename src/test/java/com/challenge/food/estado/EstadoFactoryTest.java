package com.challenge.food.estado;

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
}

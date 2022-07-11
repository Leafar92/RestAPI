package com.challenge.food.util;

import com.challenge.food.domain.model.Cidade;

public class CidadeFactoryTest {

	public static Cidade createValidCidade() {
		return Cidade.builder().id(100L).nome("ParaGuacu").estado(EstadoFactoryTest.createValidEstado()).build();
	}

	public static Cidade createCidadeToBeSaved() {
		return Cidade.builder().nome("Bahia").estado(EstadoFactoryTest.createValidEstado()).build();
	}

	public static Cidade createEstadoToBeUpdated() {
		Cidade createValidCidade = createValidCidade();
		createValidCidade.setNome("ParaGuacu 2");
		return createValidCidade;
	}
}

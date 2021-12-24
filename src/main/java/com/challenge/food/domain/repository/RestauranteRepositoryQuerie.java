package com.challenge.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.challenge.food.domain.model.Restaurante;

public interface RestauranteRepositoryQuerie {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
}

package com.challenge.food.domain.service;

import java.util.List;

import com.challenge.food.domain.model.dto.VendaDiaria;
import com.challenge.food.domain.model.input.VendaDiariaInput;

public interface ConsultaDiaria {

	List<VendaDiaria> consultaDiaria(VendaDiariaInput input);
}

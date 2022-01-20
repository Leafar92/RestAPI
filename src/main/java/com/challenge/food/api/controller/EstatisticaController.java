package com.challenge.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.domain.model.dto.VendaDiaria;
import com.challenge.food.domain.model.input.VendaDiariaInput;
import com.challenge.food.domain.service.ConsultaDiaria;

@RestController
@RequestMapping("estatisticas")
public class EstatisticaController {

	@Autowired
	private ConsultaDiaria consultaDiaria;
	
	@GetMapping
	public List<VendaDiaria> consultarVendas(VendaDiariaInput input){
		return consultaDiaria.consultaDiaria(input);
	}
}

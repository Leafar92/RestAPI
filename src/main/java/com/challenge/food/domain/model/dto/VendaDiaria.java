package com.challenge.food.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {

	private Date dataCriacao;
	private BigDecimal totalVendas;
	private Long numeroVendas;
}

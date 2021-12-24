package com.challenge.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.Data;

@Embeddable
@Data
public class Endereco {
	
	@Column(name = "endereco_rua")
	private String rua;
	@Column(name = "endereco_numero")
	private Long numero;
	@Column(name = "endereco_cep")
	private Long cep;
	
	@ManyToOne
	private Cidade cidade;
	
}

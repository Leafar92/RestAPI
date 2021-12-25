package com.challenge.food.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	private String nome;
	
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@ManyToOne
	private Cozinha cozinha;


	@Embedded
	private Endereco endereco;
	
//	@CreationTimestamp
//	private OffsetDateTime dataCadastro;
//	
//	@UpdateTimestamp
//	private OffsetDateTime dataAtualizacao;
	
	@OneToMany
	private List<Produto> produtos = new ArrayList<>();
	
	private boolean ativo = true;
	
	public void ativar() {
		this.ativo = true;
	}
	
	public void desativar() {
		this.ativo = false;
	}
}

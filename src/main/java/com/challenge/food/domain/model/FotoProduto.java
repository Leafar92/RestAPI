package com.challenge.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id")
	private Long id;
	
	private String descricao;
	
	@NotBlank
	private String contentType;
	
	@NotNull
	private Long tamanho;
	
	@NotBlank
	private String nomeArquivo;
	
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	private Produto produto;
}

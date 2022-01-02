package com.challenge.food.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	
	@ManyToMany
	@JoinTable(name = "restaurante_produto", joinColumns = @JoinColumn(name="id_restaurante"), 
	inverseJoinColumns = @JoinColumn(name= "id_produto"))
	private List<Produto> produtos = new ArrayList<>();
	
	private boolean ativo = true;
	
	private boolean aberto = true;
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", joinColumns = @JoinColumn(name="id_restaurante"),
	inverseJoinColumns =  @JoinColumn(name="id_usuario"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	
	public void associarResponsavel(Usuario u) {
		this.responsaveis.add(u);
	}
	
	public void desassociarResponsavel(Usuario u) {
		this.responsaveis.remove(u);
	}
	
	public void ativar() {
		this.ativo = true;
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	public void abrir() {
		this.aberto = true;
	}
	
	public void fechar() {
		this.aberto = false;
	}
}

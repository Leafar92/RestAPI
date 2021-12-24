package com.challenge.food.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name="grupo_id"), 
	inverseJoinColumns = @JoinColumn(name= "permissao_id"))
	private List<Permissao> permissoes = new ArrayList<>();
	
}

package com.challenge.food.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Set<Permissao> permissoes = new HashSet<>();
	
	public void associarPermissao(Permissao p) {
		permissoes.add(p);
	}
	
	public void desassociarPermissao(Permissao p) {
		permissoes.remove(p);
	}
	
}

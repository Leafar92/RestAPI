package com.challenge.food.domain.model;

import java.time.OffsetDateTime;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;

	private String email;
	
	private String senha;
	
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns =  @JoinColumn(name="id_usuario"), inverseJoinColumns = 
	@JoinColumn(name="id_grupo"))
	private Set<Grupo> grupos = new HashSet<>();
	
	public void associarGrupo(Grupo g) {
		this.grupos.add(g);
	}
	
	public void desassociarGrupo(Grupo g) {
		this.grupos.remove(g);
	}
	
	@PrePersist
	private void prePersist() {
		this.dataCadastro = OffsetDateTime.now();
	}
	
	@PreUpdate
	private void prUpdate() {
		this.dataCadastro = OffsetDateTime.now();
	}
}

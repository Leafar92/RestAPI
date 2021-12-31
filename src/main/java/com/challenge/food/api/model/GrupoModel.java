package com.challenge.food.api.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoModel {

	private Long id;

	private String nome;
	
	private Set<PermissaoModel> permissoes;

}

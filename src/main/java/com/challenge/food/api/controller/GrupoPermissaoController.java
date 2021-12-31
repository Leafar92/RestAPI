package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.assembler.GrupoInputDisassembler;
import com.challenge.food.api.assembler.GrupoModelAssembler;
import com.challenge.food.api.assembler.PermissaoInputDisassembler;
import com.challenge.food.api.assembler.PermissaoModelAssembler;
import com.challenge.food.api.assembler.ProdutoInputDisassembler;
import com.challenge.food.api.assembler.ProdutoModelAssembler;
import com.challenge.food.api.input.PermissaoInput;
import com.challenge.food.api.input.ProdutoInput;
import com.challenge.food.api.model.GrupoModel;
import com.challenge.food.api.model.PermissaoModel;
import com.challenge.food.api.model.ProdutoModel;
import com.challenge.food.domain.model.Grupo;
import com.challenge.food.domain.model.Permissao;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.service.GrupoService;
import com.challenge.food.domain.service.PermissaoService;
import com.challenge.food.domain.service.ProdutoService;
import com.challenge.food.domain.service.RestauranteService;

@RestController
@RequestMapping("grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	private PermissaoService permissaoService;
	
	
	@GetMapping
	public List<PermissaoModel> listarPermissoes(@PathVariable Long idGrupo) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);
		
		return permissaoModelAssembler.toListModel(grupo.getPermissoes());
	}
	
	@GetMapping("/{idPermissao}")
	public PermissaoModel listarPermissaoPorID(@PathVariable Long idPermissao, @PathVariable Long idGrupo) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);
		
		Permissao permissao = permissaoService.findByIdOrThrowException(idPermissao);
		
		Permissao found = grupoService.getPermissaoOrThrosException(grupo, permissao);
		
		return permissaoModelAssembler.toModel(found);
	}

	
	@PutMapping("/{idPermissao}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void associarPermissao(@PathVariable Long idPermissao, @PathVariable Long idGrupo) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);
		
		Permissao permissao = permissaoService.findByIdOrThrowException(idPermissao);
		
		 grupoService.associarPermissao(grupo, permissao);
		
	}

	
	@DeleteMapping("/{idPermissao}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void desassociarPermissao(@PathVariable Long idPermissao, @PathVariable Long idGrupo) {
		Grupo grupo = grupoService.findByIdOrThrowException(idGrupo);
		
		Permissao permissao = permissaoService.findByIdOrThrowException(idPermissao);
		
		grupoService.desassociarPermissao(grupo, permissao);
	}


}

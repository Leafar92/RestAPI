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

import com.challenge.food.api.ResourceAPI;
import com.challenge.food.api.assembler.ProdutoInputDisassembler;
import com.challenge.food.api.assembler.ProdutoModelAssembler;
import com.challenge.food.api.input.ProdutoInput;
import com.challenge.food.api.model.ProdutoModel;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.service.ProdutoService;
import com.challenge.food.domain.service.RestauranteService;

@RestController
@RequestMapping("restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public List<ProdutoModel> listarTodosProdutos(@PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.findById(idRestaurante);
		return produtoModelAssembler.toListModel(produtoService.findByRestaurante(restaurante));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProdutoModel save(@RequestBody @Valid ProdutoInput input, @PathVariable Long idRestaurante) {
		Restaurante restaurante = restauranteService.findById(idRestaurante);
		Produto produto = produtoInputDisassembler.toDomainObject(input);
		produto.setRestaurante(restaurante);
		
		produto = produtoService.save(produto);
		ResourceAPI.addUriInResponseHeader(produto.getId());
		return produtoModelAssembler.toModel(produto);
	}
	
	@GetMapping("/{idProduto}")
	public ProdutoModel listarPorIDProduto(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
	    restauranteService.findById(idRestaurante);
		Produto produto = produtoService.findByRestauranteAndId(idProduto, idRestaurante);
		return produtoModelAssembler.toModel(produto);
	}
	
	@PutMapping("/{idProduto}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ProdutoModel updateProduto(@PathVariable Long idRestaurante, 
			@PathVariable Long idProduto, @RequestBody @Valid ProdutoInput input) {
		
	    restauranteService.findById(idRestaurante);
	    
		Produto produto = produtoService.findByRestauranteAndId(idProduto, idRestaurante);
		
		produtoInputDisassembler.copyToDomainObject(produto, input);
		
		produtoService.save(produto);
		return produtoModelAssembler.toModel(produto);
	}
	
	@DeleteMapping("/{idProduto}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteProduto(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
	   
		restauranteService.findById(idRestaurante);
		Produto produto = produtoService.findByRestauranteAndId(idProduto, idRestaurante);
		
		produtoService.delete(produto);
	}


}

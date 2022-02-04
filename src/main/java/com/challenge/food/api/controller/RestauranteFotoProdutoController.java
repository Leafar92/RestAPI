package com.challenge.food.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.assembler.FotoProdutoModelAssembler;
import com.challenge.food.api.input.FotoProdutoInput;
import com.challenge.food.api.model.FotoProdutoModel;
import com.challenge.food.domain.model.FotoProduto;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.service.ProdutoService;
import com.challenge.food.infrastructure.service.CatalogoFotoProduto;

@RestController
@RequestMapping("restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteFotoProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private CatalogoFotoProduto catalogoFotoProdutoService;
	
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@Valid FotoProdutoInput input) {
		
		Produto produto = produtoService.findByRestauranteAndId(idRestaurante, idProduto);
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setContentType(input.getArquivo().getContentType());
		fotoProduto.setDescricao(input.getDescricao());
		fotoProduto.setNomeArquivo(input.getArquivo().getOriginalFilename());
		fotoProduto.setTamanho(input.getArquivo().getSize());
		fotoProduto.setProduto(produto);
		
		fotoProduto = catalogoFotoProdutoService.save(fotoProduto);
		
		return fotoProdutoModelAssembler.toModel(fotoProduto);
		

	}

}

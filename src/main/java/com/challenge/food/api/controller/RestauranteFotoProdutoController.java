package com.challenge.food.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.input.FotoProdutoInput;
import com.challenge.food.domain.service.ProdutoService;
import com.challenge.food.domain.service.RestauranteService;

@RestController
@RequestMapping("restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteFotoProdutoController {


	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void adicionarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@Valid FotoProdutoInput fotoProdutoInput) {
		
//			restauranteService.findById(idRestaurante);
//			produtoService.findByRestauranteAndId(idProduto, idRestaurante);

			String nomeArquivo = UUID.randomUUID().toString() + fotoProdutoInput.getArquivo().getOriginalFilename();
			Path pasta = Path.of("/Users/PC/OneDrive/Documentos/fotos", nomeArquivo);
			try {
			fotoProdutoInput.getArquivo().transferTo(pasta);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}

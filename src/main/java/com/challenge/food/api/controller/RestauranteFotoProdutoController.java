package com.challenge.food.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.assembler.FotoProdutoModelAssembler;
import com.challenge.food.api.input.FotoProdutoInput;
import com.challenge.food.api.model.FotoProdutoModel;
import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.model.FotoProduto;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.service.CatalogoFotoProduto;
import com.challenge.food.domain.service.FotoProdutoRepository;
import com.challenge.food.domain.service.ProdutoService;
import com.challenge.food.domain.service.StorageService;

@RestController
@RequestMapping("restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteFotoProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@Autowired
	private FotoProdutoRepository catalogoFotoProdutoService;

	@Autowired
	private StorageService storageService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
			@Valid FotoProdutoInput input) throws IOException {

		Produto produto = produtoService.findByRestauranteAndId(idRestaurante, idProduto);

		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setContentType(input.getArquivo().getContentType());
		fotoProduto.setDescricao(input.getDescricao());
		fotoProduto.setNomeArquivo(input.getArquivo().getOriginalFilename());
		fotoProduto.setTamanho(input.getArquivo().getSize());
		fotoProduto.setProduto(produto);

		fotoProduto = catalogoFotoProdutoService.save(fotoProduto, input.getArquivo().getInputStream());

		return fotoProdutoModelAssembler.toModel(fotoProduto);

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel getFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		FotoProduto fotoProduto = catalogoFotoProdutoService.getFoto(idRestaurante, idProduto);
		return fotoProdutoModelAssembler.toModel(fotoProduto);
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> devolveFoto(@PathVariable Long idRestaurante,
			@PathVariable Long idProduto, @RequestHeader(name = "accept") String mediasType)
			throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.getFoto(idRestaurante, idProduto);

			List<MediaType> mediasAllowed = MediaType.parseMediaTypes(mediasType);

			MediaType midiaFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			verificarCompatibilidade(mediasAllowed, midiaFoto);

			InputStream inputStream = storageService.recuperar(fotoProduto.getNomeArquivo());
			return ResponseEntity.ok().contentType(midiaFoto).body(new InputStreamResource(inputStream));

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificarCompatibilidade(List<MediaType> mediasAllowed, MediaType midiaFoto)
			throws HttpMediaTypeNotAcceptableException {
		boolean isAllowed = mediasAllowed.stream().anyMatch(m -> m.isCompatibleWith(midiaFoto));

		if (!isAllowed) {
			throw new HttpMediaTypeNotAcceptableException(mediasAllowed);
		}
	}

	@DeleteMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
		catalogoFotoProdutoService.delete(idRestaurante, idProduto);
	}

}

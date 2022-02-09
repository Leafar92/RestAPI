package com.challenge.food.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.exception.FotoProdutoNaoEncontradaException;
import com.challenge.food.domain.model.FotoProduto;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.service.StorageService.NovaFoto;

@Service
public class CatalogoFotoProduto implements FotoProdutoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private StorageService fotoStorageService;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto, InputStream inputStream) {
		Produto produto = fotoProduto.getProduto();

		String novoNomeArquivo = fotoStorageService.createNomeArquivo(fotoProduto.getNomeArquivo());
		Optional<FotoProduto> fotoProdutoBD = findFotoProduto(produto.getRestaurante().getId(), produto.getId());
		String nomeArquivoFound = null;

		if (fotoProdutoBD.isPresent()) {
			nomeArquivoFound = fotoProdutoBD.get().getNomeArquivo();
			delete(fotoProdutoBD.get());
		}

		fotoProduto.setNomeArquivo(novoNomeArquivo);
		fotoProduto = manager.merge(fotoProduto);
		manager.flush();

		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(fotoProduto.getNomeArquivo()).inputStream(inputStream)
				.build();
		fotoStorageService.substituir(nomeArquivoFound, novaFoto);

		return fotoProduto;

	}

	@Transactional
	@Override
	public void delete(FotoProduto fotoProduto) {
		manager.remove(fotoProduto);
	}

	@Transactional
	public void delete(Long idRestaurante, Long idProduto) {
		FotoProduto produtoFound = getFoto(idRestaurante, idProduto);
		delete(produtoFound);
		fotoStorageService.deletar(produtoFound.getNomeArquivo());
	}

	private Optional<FotoProduto> findFotoProduto(Long idRestaurante, Long idProduto) {
		FotoProduto fotoProduto = null;

		try {
			String sql = "SELECT f FROM FotoProduto f join f.produto p where f.id = :idProduto and p.restaurante.id = :idRestaurante";

			fotoProduto = manager.createQuery(sql, FotoProduto.class).setParameter("idProduto", idProduto)
					.setParameter("idRestaurante", idRestaurante).getSingleResult();

		} catch (NoResultException e) {
			return Optional.empty();
		}
		return Optional.of(fotoProduto);
	}

	@Override
	public FotoProduto getFoto(Long idRestaurante, Long idProduto) {
		return findFotoProduto(idRestaurante, idProduto).orElseThrow(() -> new FotoProdutoNaoEncontradaException(
				String.format("A foto do restaurante de id %d do produto %s nao existe", idRestaurante, idProduto)));
	}

}

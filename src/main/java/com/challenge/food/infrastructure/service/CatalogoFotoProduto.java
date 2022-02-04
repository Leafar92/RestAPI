package com.challenge.food.infrastructure.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.food.domain.model.FotoProduto;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.service.FotoProdutoRepository;

@Service
public class CatalogoFotoProduto implements FotoProdutoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto) {
		Produto produto = fotoProduto.getProduto();

		Optional<FotoProduto> fotoProdutoBD = findFotoProduto(produto.getRestaurante().getId(), produto.getId());

		if (fotoProdutoBD.isPresent()) {
			delete(fotoProdutoBD.get());
		}

		manager.flush();

		return manager.merge(fotoProduto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto fotoProduto) {
		manager.remove(fotoProduto);
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

}

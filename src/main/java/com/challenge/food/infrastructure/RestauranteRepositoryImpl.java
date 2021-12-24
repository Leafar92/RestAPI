package com.challenge.food.infrastructure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root
;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.repository.RestauranteRepositoryQuerie;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuerie {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

	CriteriaQuery<Restaurante> createQuery = builder.createQuery(Restaurante.class);

	Root<Restaurante> from = createQuery.from(Restaurante.class);

	var listPredicate = new ArrayList<Predicate>();

	if(StringUtils.hasLength(nome)) {
	listPredicate.add(builder.like(from.get("nome"), "%" + nome + "%"));
	}
	
	if(taxaInicial != null) {
		listPredicate.add(builder.greaterThanOrEqualTo(from.get("taxaFrete"), taxaInicial));
	}
	
	if(taxaFinal != null) {
		listPredicate.add(builder.lessThanOrEqualTo(from.get("taxaFrete"), taxaFinal));
	}

		createQuery.where(listPredicate.toArray(new Predicate[0]));
		
		return manager.createQuery(createQuery).getResultList();
	}
}

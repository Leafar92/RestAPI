package com.challenge.food.infrastructure.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.model.StatusPedido;
import com.challenge.food.domain.model.dto.VendaDiaria;
import com.challenge.food.domain.model.input.VendaDiariaInput;
import com.challenge.food.domain.service.ConsultaDiaria;

@Repository
public class ConsultaDiariaImpl implements ConsultaDiaria {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<VendaDiaria> consultaDiaria(VendaDiariaInput input) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		
		var functonDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao")); 
		
		
		var selection = builder.construct(VendaDiaria.class, 
				functonDateDataCriacao,
				builder.sum(root.get("valorTotal")),
				builder.count(root.get("id")));
		
		
		if(input.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), input.getRestauranteId()));
		}
		
		if(input.getDataCriacaoInicio()!= null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), input.getDataCriacaoInicio()));
		}
		
		predicates.add(root.get("statusPedido").in(Arrays.asList(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE)));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functonDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
		
		
		
		
	}
	
}

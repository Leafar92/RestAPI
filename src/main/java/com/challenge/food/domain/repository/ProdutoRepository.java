package com.challenge.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query("from Produto where restaurante.id = :restauranteId and id = :id")
	Optional<Produto> findByIDAndRestaurante(@Param ("restauranteId") Long restauranteId, @Param("id") Long id);
	
	List<Produto> findByRestaurante(Restaurante restaurante);
}

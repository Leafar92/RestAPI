package com.challenge.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.food.domain.model.Estado;

@Repository
public interface EstadoRespository extends JpaRepository<Estado, Long> {

	
	@Query("from Estado where nome = :nome")
	Optional<Estado> findByName(@Param("nome") String nome);
}

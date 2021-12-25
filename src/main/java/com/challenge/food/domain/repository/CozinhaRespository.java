package com.challenge.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.food.domain.model.Cozinha;

@Repository
public interface CozinhaRespository extends JpaRepository<Cozinha, Long> {

	@Query("from Cozinha where nome = :nome")
	Optional<Cozinha> findByName(@Param("nome") String nome);
}

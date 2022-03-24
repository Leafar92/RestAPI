package com.challenge.food.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.food.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {


	@Query("from FormaPagamento where nome = :nome")
	Optional<FormaPagamento> findByName(@Param("nome") String nome);
	
	@Query("Select max(dataAtualizacao) from FormaPagamento")
	OffsetDateTime getDataAtualizacaoMaxima();
}

package com.challenge.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.food.domain.model.Estado;

@Repository
public interface EstadoRespository extends JpaRepository<Estado, Long> {

}

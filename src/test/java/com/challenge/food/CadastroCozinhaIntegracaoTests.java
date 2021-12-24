package com.challenge.food;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.model.Cozinha;
import com.challenge.food.domain.repository.CozinhaRespository;
import com.challenge.food.domain.service.CozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
class CadastroCozinhaIntegracaoTests {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaRespository cozinhaRespository;
	
	@Before
	public void setUp() {
		cozinhaRespository.deleteAll();
	}

	@Test
	public void saveCozinhaWithSuccessWhenDataAreValids() {
		Cozinha c = new Cozinha();
		c.setNome("Baiana");

		cozinhaService.save(c);

		assertThat(c).isNotNull();
		assertThat(c.getNome()).isNotNull();
		assertThat(c.getId()).isNotNull();
	}

	@Test()
	public void shoulThrowsExceptinoWhenDataAreInvalids() {
		Cozinha c = new Cozinha();

		assertThrows(ConstraintViolationException.class, () -> cozinhaService.save(c));

	}

	@Test
	public void shoulThrowsExceptionWhenIdIsNotFound() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> cozinhaService.findById(1L));
	}
	
//	@Test
//	public void shoulThrowsExceptionWhenCozinhaIsInUse() {
//		assertThrows(SQLException.class, () -> cozinhaService.delete(1L));
//	}

}

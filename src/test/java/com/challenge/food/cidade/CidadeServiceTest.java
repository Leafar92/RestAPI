package com.challenge.food.cidade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.challenge.food.domain.exception.CidadeNaoEncontradaException;
import com.challenge.food.domain.model.Cidade;
import com.challenge.food.domain.repository.CidadeRepository;
import com.challenge.food.domain.service.CidadeService;
import com.challenge.food.domain.service.EstadoService;
import com.challenge.food.util.CidadeFactoryTest;
import com.challenge.food.util.EstadoFactoryTest;

@ExtendWith(SpringExtension.class)
public class CidadeServiceTest {

	@InjectMocks
	private CidadeService cidadeService;

	@Mock
	private CidadeRepository cidadeRepository;
	
	@Mock
	EstadoService estadoService;

	@BeforeEach
	void setUp() {
		BDDMockito.when(cidadeRepository.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(CidadeFactoryTest.createValidCidade()));

		BDDMockito.when(cidadeRepository.save(ArgumentMatchers.any(Cidade.class)))
				.thenReturn(CidadeFactoryTest.createValidCidade());

		BDDMockito.doNothing().when(cidadeRepository).delete(ArgumentMatchers.any(Cidade.class));

		BDDMockito.when(cidadeRepository.save(ArgumentMatchers.any(Cidade.class)))
				.thenReturn(CidadeFactoryTest.createValidCidade());
		
		BDDMockito.when(estadoService.findByIdOrThrowException(ArgumentMatchers.anyLong()))
		.thenReturn(EstadoFactoryTest.createValidEstado());
		
	}

	@Test
	void itShouldReturnCidadeWhenIdIsValid() {
		Cidade found = cidadeService.findById(7L);
		Cidade expected = CidadeFactoryTest.createValidCidade();
		assertThat(found).isNotNull().isEqualTo(expected);
		assertThat(found.getId()).isEqualTo(expected.getId());

	}

	@Test
	void itShouldSaveCidadeWhendValid() {
		Cidade saved = cidadeService.save(CidadeFactoryTest.createCidadeToBeSaved());

		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
	}

	@Test
	void itShouldThrowsExceptionWhenIsInotValid() {
		BDDMockito.when(cidadeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(CidadeNaoEncontradaException.class).isThrownBy(() -> cidadeService.findById(1L));

	}

	@Test
	void itShouldDeleteWhenValid() {
		assertThatCode(() -> cidadeService.delete(9L)).doesNotThrowAnyException();
	}

	@Test
	void itShouldUpdateCidade() {
		assertThatNoException().isThrownBy(() -> cidadeService.update(CidadeFactoryTest.createValidCidade()));

	}
}

package com.challenge.food.estado;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.challenge.food.domain.exception.EstadoNaoEncontradoException;
import com.challenge.food.domain.exception.RecursoCadastradoException;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;
import com.challenge.food.domain.service.EstadoService;
import com.challenge.food.util.EstadoFactoryTest;

@ExtendWith(SpringExtension.class)
public class EstadoServiceTest {

	@InjectMocks
	private EstadoService estadoService;

	@Mock
	private EstadoRespository estadoRespositoryMock;

	@BeforeEach
	void setUp() {
		BDDMockito.when(estadoRespositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(EstadoFactoryTest.createValidEstado()));

		BDDMockito.when(estadoRespositoryMock.save(ArgumentMatchers.any(Estado.class)))
				.thenReturn(EstadoFactoryTest.createValidEstado());

		BDDMockito.doNothing().when(estadoRespositoryMock).delete(ArgumentMatchers.any(Estado.class));

		BDDMockito.when(estadoRespositoryMock.save(ArgumentMatchers.any(Estado.class)))
				.thenReturn(EstadoFactoryTest.createEstadoUpdated());
	}

	@Test
	void itShouldReturnEstadoWhenIdIsValid() {
		Estado found = estadoService.findByIdOrThrowException(7L);
		Estado expected = EstadoFactoryTest.createValidEstado();
		assertThat(found).isNotNull().isEqualTo(expected);
		assertThat(found.getId()).isEqualTo(expected.getId());

	}

	@Test
	void itShouldSaveEstadoWhendValid() {
		Estado saved = estadoService.save(EstadoFactoryTest.createEstadoToBeSaved());

		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
	}

	@Test
	void itShouldThrowsExceptionWhenIsInotValid() {
		BDDMockito.when(estadoRespositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(EstadoNaoEncontradoException.class)
				.isThrownBy(() -> estadoService.findByIdOrThrowException(1L));

	}

	@Test
	void itShouldDeleteWhenValid() {
		assertThatCode(() -> estadoService.delete(9L)).doesNotThrowAnyException();
	}

	@Test
	void itShouldThrowsExceptionWhenNameIsNotValid() {
		BDDMockito.when(estadoRespositoryMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(Optional.of(EstadoFactoryTest.createValidEstado()));

		assertThatExceptionOfType(RecursoCadastradoException.class)
				.isThrownBy(() -> estadoService.verifyByName("Ivalid Name"));

	}

	@Test
	void itShouldUpdateEstado() {

		assertThatNoException().isThrownBy(() -> estadoService.update(EstadoFactoryTest.createValidEstado()));

	}
}

package com.challenge.food.estado;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.challenge.food.domain.exception.RecursoCadastradoException;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;
import com.challenge.food.domain.service.EstadoService;

@ExtendWith(SpringExtension.class)
public class EstadoServiceTest {

	
	@InjectMocks
	private EstadoService estadoService;
	
	@Mock
	private EstadoRespository estadoRespositoryMock;
	
	
	@BeforeEach
	void setUp() {
		BDDMockito
		.when(estadoRespositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(EstadoFactoryTest.createValidEstado()));
		
		BDDMockito.when(estadoRespositoryMock.save(ArgumentMatchers.any(Estado.class)))
		.thenReturn(EstadoFactoryTest.createValidEstado());
		
		BDDMockito.doNothing().when(estadoRespositoryMock).delete(ArgumentMatchers.any(Estado.class));
	}
	
	@Test
	void itShouldReturnEstadoWhenFindByIdValid() {
		 Estado found = estadoService.findByIdOrThrowException(7L);
		 Estado expected = EstadoFactoryTest.createValidEstado();
		 assertThat(found).isNotNull();
		 assertThat(expected).isEqualTo(found);
		 assertThat(found.getId()).isEqualTo(expected.getId());
		
	}
	
	@Test
	void itShouldSaveEstadoWhendValid() {
		 Estado saved = estadoService.save(EstadoFactoryTest.createEstadoToBeSaved());
		
		 assertThat(saved).isNotNull();
		 assertThat(saved.getId()).isNotNull();
	}
	
	@Test
	void itShouldThrowsExceptionWhenFailed() {
		 BDDMockito.when(estadoRespositoryMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(Optional.of(EstadoFactoryTest.createValidEstado()));
		 
		 
		assertThatExceptionOfType(RecursoCadastradoException.class)
		.isThrownBy(()-> estadoService.verifyByName("zaza"));
		
	}
	
	@Test
	void itShouldDeleteWhenValid() {
		assertThatCode(() -> estadoService.delete(9L)).doesNotThrowAnyException();
	}
	
	
	
}

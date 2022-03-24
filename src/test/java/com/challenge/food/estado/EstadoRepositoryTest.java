package com.challenge.food.estado;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.challenge.food.api.assembler.EstadoModelAssembler;
import com.challenge.food.api.controller.EstadoController;
import com.challenge.food.api.model.EstadoModel;

import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.service.EstadoService;

@ExtendWith(SpringExtension.class)
public class EstadoRepositoryTest {

	@InjectMocks
	private EstadoController estadoControllerMock;
	
	@Mock
	private EstadoService estadoService;
	
	@Mock
	private  EstadoModelAssembler estadoModelAssembler;
	
	
	@BeforeEach
	void setUp() {
		OngoingStubbing<Estado> thenReturn = BDDMockito
		.when(estadoService.findByIdOrThrowException(ArgumentMatchers.anyLong()))
		.thenReturn(EstadoFactoryTest.createValidEstado());
	}
	
	@Test
	public void itShouldFindEstadoByName() {
		//given
		EstadoModel found = estadoControllerMock.findById(2L);
		
		String nomeExpected = EstadoFactoryTest.createValidEstado().getNome();
		
		//when
		
		
		
		//then
		assertThat(found).isNotNull();
		assertThat(found.getNome()).isEqualTo(nomeExpected);
	}
}

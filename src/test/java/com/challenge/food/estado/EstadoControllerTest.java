package com.challenge.food.estado;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.challenge.food.api.assembler.EstadoInputDisassembler;
import com.challenge.food.api.assembler.EstadoModelAssembler;
import com.challenge.food.api.controller.EstadoController;
import com.challenge.food.api.input.EstadoInput;
import com.challenge.food.api.model.EstadoModel;
import com.challenge.food.domain.exception.EstadoNaoEncontradoException;
import com.challenge.food.domain.exception.RecursoCadastradoException;
import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;
import com.challenge.food.domain.service.EstadoService;
import com.challenge.food.util.EstadoFactoryTest;

@ExtendWith(SpringExtension.class)
public class EstadoControllerTest {

	@InjectMocks
	private EstadoController estadoController;

	@Mock
	private EstadoService estadoService;
	
	@Mock
	private EstadoRespository estadoRepository;
	
	@Mock
	private  EstadoInputDisassembler estadoInputDisassembler;
	
	@Mock
	private EstadoModelAssembler estadoModelAssembler;

	@BeforeEach
	void setUp() {
		BDDMockito.when(estadoRepository.findAll())
				.thenReturn(Arrays.asList(EstadoFactoryTest.createValidEstado()));

		BDDMockito.when(estadoService.save(ArgumentMatchers.any(Estado.class)))
				.thenReturn(EstadoFactoryTest.createValidEstado());

		BDDMockito.when(estadoInputDisassembler.toDomainObject(ArgumentMatchers.any(EstadoInput.class)))
		.thenReturn(EstadoFactoryTest.createValidEstado());
		
		BDDMockito.when(estadoModelAssembler.toModel(ArgumentMatchers.any(Estado.class)))
		.thenReturn(EstadoFactoryTest.createValidEstadoModel());
		BDDMockito.doNothing().when(estadoService).delete(ArgumentMatchers.anyLong());
		
		
		BDDMockito.when(estadoModelAssembler.toListModel(ArgumentMatchers.any()))
		.thenReturn(Arrays.asList(EstadoFactoryTest.createValidEstadoModel()));
		
		BDDMockito.when(estadoService.findByIdOrThrowException(ArgumentMatchers.anyLong()))
		.thenReturn(EstadoFactoryTest.createValidEstado());
		
	}

	@Test
	void itShouldReturnListOfEstado() {
		List<EstadoModel> listEstado = estadoController.listar();
		assertThat(listEstado)
		.isNotEmpty()
		.hasSize(1);
		
		assertThat(listEstado.get(0).getNome())
		.isEqualTo(EstadoFactoryTest.createValidEstado().getNome());

	}

	@Test
	@Disabled
	void itShouldSaveEstadoWhendValid() {
		EstadoModel saved = estadoController.save(EstadoFactoryTest.createEstadoInput());

		assertThat(saved).isNotNull();
		
		assertThat(saved.getId()).isEqualTo(EstadoFactoryTest.createValidEstadoModel().getId());
	}
	
	@Test
	void itShouldReturnEstadoWhenIDIsValid() {

	EstadoModel findById = estadoController.findById(1L);
	
	assertThat(findById).isNotNull();
	Estado estadoToBeCompared = EstadoFactoryTest.createValidEstado();
	assertThat(findById.getId()).isEqualTo(estadoToBeCompared.getId());
	assertThat(findById.getNome()).isEqualTo(estadoToBeCompared.getNome());

	}

	@Test
	void itShouldThrowsExceptionWhenIDIsInotValid() {
		BDDMockito.when(estadoService.findByIdOrThrowException(ArgumentMatchers.anyLong()))
		.thenThrow(EstadoNaoEncontradoException.class);

		assertThatExceptionOfType(EstadoNaoEncontradoException.class)
				.isThrownBy(() -> estadoController.findById(1L));

	}
//
//	@Test
//	void itShouldDeleteWhenValid() {
//		assertThatCode(() -> estadoController.delete(9L)).doesNotThrowAnyException();
//	}
//
//	@Test
//	void itShouldThrowsExceptionWhenNameIsNotValid() {
//		BDDMockito.when(estadoService.findByName(ArgumentMatchers.anyString()))
//				.thenReturn(Optional.of(EstadoFactoryTest.createValidEstado()));
//
//		assertThatExceptionOfType(RecursoCadastradoException.class)
//				.isThrownBy(() -> estadoController.verifyByName("Ivalid Name"));
//
//	}
//
//	@Test
//	void itShouldUpdateEstado() {
//
//		assertThatNoException().isThrownBy(() -> estadoController.update(EstadoFactoryTest.createValidEstado()));
//
//	}
}

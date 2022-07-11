package com.challenge.food.estado;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.challenge.food.domain.model.Estado;
import com.challenge.food.domain.repository.EstadoRespository;
import com.challenge.food.util.EstadoFactoryTest;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EstadoRepositoryTest {


	@Autowired
	private  EstadoRespository estadoRepository;
	
	
	
	@Test
	@Disabled
	public void itShouldFindEstadoByName() {
		//given
		Estado e = estadoRepository.save(EstadoFactoryTest.createEstadoToBeSaved());
		
		String nomeExpected = EstadoFactoryTest.createEstadoToBeSaved().getNome();
		
		//when
		Optional<Estado> estadoFound = estadoRepository.findByName(nomeExpected);
		
		
		//Then
		assertThat(estadoFound).isNotEmpty();
		
		assertThat(estadoFound.get().getId()).isNotNull();
		
		assertThat(estadoFound.get().getNome()).isEqualTo(nomeExpected);
		
		
	}
}

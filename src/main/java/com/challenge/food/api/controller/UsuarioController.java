package com.challenge.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.food.api.ResourceAPI;
import com.challenge.food.api.assembler.UsuarioInputDisassembler;
import com.challenge.food.api.assembler.UsuarioModelAssembler;
import com.challenge.food.api.input.UsuarioInput;
import com.challenge.food.api.input.UsuarioSenha;
import com.challenge.food.api.input.UsuarioUpdate;
import com.challenge.food.api.model.UsuarioModel;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.model.Usuario;
import com.challenge.food.domain.repository.UsuarioRepository;
import com.challenge.food.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService userService;

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private UsuarioModelAssembler userModelAssembler;

	@Autowired
	private UsuarioInputDisassembler userInputDisassembler;

	@GetMapping
	public List<UsuarioModel> listar() {
		return userModelAssembler.toListModel(userRepository.findAll());
	}

	@GetMapping("/{idUser}")
	public UsuarioModel findById(@PathVariable Long idUser) {
		return userModelAssembler.toModel(userService.findByIdOrThrowException(idUser));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UsuarioModel save(@RequestBody @Valid UsuarioInput input) {
		userService.validarSenha(input.getSenha(), input.getSenhaConfirmacao());
		Usuario user = userInputDisassembler.toDomainObject(input);
		ResourceAPI.addUriInResponseHeader(user.getId());
		return userModelAssembler.toModel(userService.save(user));
	}

	@PutMapping("/{idUser}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long idUser, @RequestBody UsuarioUpdate input) {
		Usuario user = userService.findByIdOrThrowException(idUser);

		userInputDisassembler.copyToDomainObject(user, input);

		userService.update(user);

	}
	
	@PutMapping("/{idUser}/alteracao-senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateSenha(@PathVariable Long idUser, @RequestBody UsuarioSenha input) {
		Usuario user = userService.findByIdOrThrowException(idUser);
		userService.validarSenha(input.getSenha(), input.getSenhaConfirmacao());
		user.setSenha(input.getSenha());
		userService.update(user);

	}

	@DeleteMapping("/{idUser}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idUser) {
		try {
			userService.delete(idUser);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(
					String.format("O Usuario de ID %d nao pode ser excluido", idUser));
		}
	}
	


}

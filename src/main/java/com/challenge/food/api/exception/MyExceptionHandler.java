package com.challenge.food.api.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.challenge.food.domain.exception.EntidadeNaoEncontradaException;
import com.challenge.food.domain.exception.NegocioException;
import com.challenge.food.domain.exception.RecursoEmUsoException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problem.Field> erros = ex.getBindingResult().getFieldErrors().stream()
				.map(e ->{
					String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
					
				 return Problem.Field.builder()
				.fieldName(e.getField())
			    .userMessage(message)
			    .build();
				}
			    )
				.collect(Collectors.toList());
		
		Problem problem= createProblem(status.value(), "Errro nos campos",
				ProblemType.DADOS_INVALIDOS)
				.erros(erros)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> tratarExceptionGeneric(Exception e, WebRequest request) {

		String msg = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o erro persistir, contacte o adm.";
		Problem body = createProblem(HttpStatus.NOT_FOUND.value(), msg, ProblemType.ERRO_DE_SISTEMA)
				.build();

		return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable error = ExceptionUtils.getRootCause(ex);

		if (error instanceof InvalidFormatException) {
			return invalidFormatException((InvalidFormatException) error, headers, status, request);
		}

		if (error instanceof PropertyBindingException) {
			return propertyBindingException((PropertyBindingException) error, headers, status, request);
		}

		Problem body = createProblem(status.value(), "Corpo da mensagem na requisicao invalida",
				ProblemType.CORPO_MENSAGEM_INVALIDO).build();

		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		
		Throwable t = ex.getRootCause();
		
		if(t instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException)t, 
					headers, status, request);
		}

		String message = String.format("A parametro de url %s recebeu o valor invalido %s. Insira um valor do tipo %s",
				ex.getMostSpecificCause().getCause(), ex.getValue(), ex.getRequiredType().getSimpleName());

	
		Problem problem = createProblem(status.value(), message, problemType).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String message = String.format("O parametro de url %s recebeu o valor invalido %s. Insira um valor do tipo %s",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblem(status.value(), message, ProblemType.PARAMETRO_INVALIDO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	private ResponseEntity<Object> propertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(e -> e.getFieldName()).collect(Collectors.joining("."));
		String description = String.format("A propriedade %s nao existe", path);

		Problem problem = createProblem(status.value(), description, ProblemType.CORPO_MENSAGEM_INVALIDO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> invalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(e -> e.getFieldName()).collect(Collectors.joining("."));
		String description = String.format("A propriedade %s recebeu o valor invalido %s onde o tipo esperado era %s",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblem(status.value(), description, ProblemType.CORPO_MENSAGEM_INVALIDO).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request) {

		Problem body = createProblem(HttpStatus.NOT_FOUND.value(), e.getMessage(), ProblemType.ENTIDADE_NAO_ENCONTRADA)
				.build();

		return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	@ExceptionHandler(RecursoEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada(RecursoEmUsoException e, WebRequest request) {
		Problem body = createProblem(HttpStatus.CONFLICT.value(), e.getMessage(), ProblemType.RECURSO_EM_USO).build();
		return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada(NegocioException e, WebRequest request) {

		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body instanceof String) {
			body = Problem.builder().title(status.getReasonPhrase()).data(LocalDateTime.now()).build();
		} else if (body == null) {
			body = Problem.builder().title((String) body).data(LocalDateTime.now()).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblem(Integer status, String description, ProblemType problemType) {
		return Problem.builder().data(LocalDateTime.now()).description(description).status(status)
				.title(problemType.getTitle()).type(problemType.getUri());
	}
}

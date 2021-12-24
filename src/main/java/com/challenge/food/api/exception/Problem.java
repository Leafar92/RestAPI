package com.challenge.food.api.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@JsonInclude(content = Include.NON_NULL)
@Data
@Builder
public class Problem {

	private String type;
	private Integer status;
	private String title;
	private String description;
	private LocalDateTime data;
	private List<Field> erros;
	
	@Getter
	@Builder
	public static class Field{
		private String fieldName;
		private String userMessage;
		
	}
}

package com.challenge.food.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;

@Validated
@Component
@ConfigurationProperties("food.email")
@Getter
public class EmailProperties {

	@NotNull
	private String remetente;
}

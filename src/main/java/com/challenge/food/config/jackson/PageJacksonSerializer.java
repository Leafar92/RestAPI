package com.challenge.food.config.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJacksonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		
		gen.writeObjectField("content", page.getContent());
		gen.writeNumberField("size", page.getNumberOfElements());
		gen.writeNumberField("page", page.getNumber());
		gen.writeNumberField("totalPages", page.getTotalPages());
		
		gen.writeEndObject();
		
	}

}

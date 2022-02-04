package com.challenge.food.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.food.core.validation.FileSize;
import com.challenge.food.core.validation.FileType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	//@NotNull
//	@FileSize(max = "20KB")
	@FileType(types = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	private MultipartFile arquivo;

	@NotBlank
	private String descricao;

}

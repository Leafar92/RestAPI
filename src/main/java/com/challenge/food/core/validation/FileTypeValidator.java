package com.challenge.food.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

	
	
	private  List<String> typesAllowed;
	
	@Override
	public void initialize(FileType constraintAnnotation) {
		this.typesAllowed= Arrays.asList(constraintAnnotation.types());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || typesAllowed.contains(value.getContentType());
	}
}

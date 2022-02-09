package com.challenge.food.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.challenge.food.domain.exception.StorageException;
import com.challenge.food.domain.service.StorageService;

@Service
public class LocalStorageService implements StorageService {

//	@Value("${food.storage.local.diretorio}")
//	private Path diretorio;

	@Override
	public void armazenar(NovaFoto novaFoto) {

		try {
			Path dir = createAbosultePath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(dir));
		} catch (IOException e) {
			throw new StorageException("Nao foi possivel realizar o armazenamento", e);
		}
	}

	private Path createAbosultePath(String nomeArquivo) {
		return Path.of("/Users/PC/OneDrive/Área de Trabalho/catalogo").resolve(Path.of(nomeArquivo));
	}

	@Override
	public void deletar(String nomeArquivo) {

		try {
			Path path = createAbosultePath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Nao foi possivel deletar a foto", e);
		}
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path path = createAbosultePath(nomeArquivo);
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new StorageException("Nao foi possivel obter a foto", e);
		}
	}

}

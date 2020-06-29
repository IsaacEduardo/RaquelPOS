package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String DIRECTORIO_UPLOAD = "uploads";

	@Override
	public Resource carregar(String nomeFoto) throws MalformedURLException {
		Path caminhoArquivo = getPath(nomeFoto);
		log.info(caminhoArquivo.toString());

		Resource recurso = new UrlResource(caminhoArquivo.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			caminhoArquivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			recurso = new UrlResource(caminhoArquivo.toUri());
			log.error("Erro ao carregar a imagem: " + nomeFoto);
		}
		return recurso;
	}

	@Override
	public String copiar(MultipartFile arquivo) throws IOException {

		String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename().replace(" ", " ");

		Path caminhoArquivo = getPath(nomeArquivo);
		log.info(caminhoArquivo.toString());

		Files.copy(arquivo.getInputStream(), caminhoArquivo);

		return nomeArquivo;
	}

	@Override
	public boolean eliminar(String nomeFoto) {
		if (nomeFoto != null && nomeFoto.length() > 0) {
			Path caminhoFotoAnterior = Paths.get("uploads").resolve(nomeFoto).toAbsolutePath();
			File arquivoFotoAnterior = caminhoFotoAnterior.toFile();

			if (arquivoFotoAnterior.exists() && arquivoFotoAnterior.canRead()) {
				arquivoFotoAnterior.delete();

				return true;
			}

		}

		return false;
	}

	@Override
	public Path getPath(String nomeFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nomeFoto).toAbsolutePath();
	}

}

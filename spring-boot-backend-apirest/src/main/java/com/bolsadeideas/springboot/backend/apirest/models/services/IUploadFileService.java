package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	public Resource carregar(String nomeFoto) throws MalformedURLException;
	public String copiar (MultipartFile arquivo) throws IOException;
	public boolean eliminar(String nomeFoto);
	public Path getPath(String nomeFoto);
}

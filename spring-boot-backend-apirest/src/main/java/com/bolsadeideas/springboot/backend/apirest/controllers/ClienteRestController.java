package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Region;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;
import com.bolsadeideas.springboot.backend.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = { "http://localhost:4200" }) // Cor para acessar a api pelo angular via http
@RestController
@RequestMapping("/api") // mapear o rest controller
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	@Autowired
	private IUploadFileService uploadService;

	// private final Logger log =
	// LoggerFactory.getLogger(ClienteRestController.class);

	// arquivo

	// metodo index para listar clientes
	@GetMapping("/clientes") // mapear url
	public List<Cliente> index() {
		return clienteService.findAll();
	}

// cliente com paginacao
	@GetMapping("/clientes/page/{page}") // mapear url
	public Page<Cliente> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return clienteService.findAll(pageable);
	}

	// buscar por id
	// adcionado regras de permições
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable long id) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error ao realizarf a consulta na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {// id.toString().concat
			response.put("mensaje", "O cliente ID:".concat(("Não existe na base de dados!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	// salvar

	// adcionado regras de permições
	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();

		// adicionando validação
		if (result.hasErrors()) {
			/*
			 * List<String> errors = new ArrayList<>(); for (FieldError err :
			 * result.getFieldErrors()) { errors.add(" O campo '" + err.getField() + "' " +
			 * err.getDefaultMessage()); }
			 */
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> " O campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {

			response.put("mensaje", "Erro ao inserir os dados na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente criado com sucesso!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// actualizar
	// adcionado regras de permições
	@Secured("ROLE_ADMIN")
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdate = null;
		Map<String, Object> response = new HashMap<>();

		// adicionando validação
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> " O campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		// mensagem
		if (clienteActual == null) {// id.toString().concat
			response.put("mensaje", "Erro: não foi posivel editar O cliente ID:"
					.concat(id.toString().concat("Não existe na base de dados!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			clienteActual.setApelido(cliente.getApelido());
			clienteActual.setNome(cliente.getNome());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteActual.setRegion(cliente.getRegion());

			clienteUpdate = clienteService.save(clienteActual);
		} catch (DataAccessException e) {

			response.put("mensaje", "Erro ao atualizar o cliente na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Cliente actualizado com sucesso!");
		response.put("cliente", clienteUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	// delecte
	// adcionado regras de permições
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			Cliente cliente = clienteService.findById(id);

			String nomeFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nomeFotoAnterior);

			clienteService.delete(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Erro ao eliminar o cliente na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "O cliente foi eliminado sucesso!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	// upload de arquivo e imagens
	// adcionado regras de permições
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam("id") Long id) {

		Map<String, Object> response = new HashMap<>();
		Cliente cliente = clienteService.findById(id);

		if (!arquivo.isEmpty()) {
			String nomeArquivo = null;
			try {
				nomeArquivo = uploadService.copiar(arquivo);
			} catch (IOException e) {
				response.put("mensaje", "Erro ao carregar a imagem do cliente");
				response.put("error", e.getMessage().concat(" : ".concat(e.getCause().getMessage())));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nomeFotoAnterior = cliente.getFoto();

			uploadService.eliminar(nomeFotoAnterior);

			cliente.setFoto(nomeArquivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "A imagem foi carregada correctamente:" + nomeArquivo);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// metodo para mostrar arquivo ou imagem
	@GetMapping("/uploads/img/{nomeFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nomeFoto) {

		Resource recurso = null;
		try {
			recurso = uploadService.carregar(nomeFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

	// metodo index para listar clientes
	// adcionado regras de permições
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/clientes/regiones") // mapear url
	public List<Region> listarRegiones() {
		return clienteService.findAllRegiones();
	}
}

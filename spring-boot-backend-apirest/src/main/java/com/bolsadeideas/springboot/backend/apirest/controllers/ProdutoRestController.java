package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;
import com.bolsadeideas.springboot.backend.apirest.models.services.IProdutoService;

@CrossOrigin(origins = { "http://localhost:4200" }) // Cor para acessar a api pelo angular via http
@RestController
@RequestMapping("/api") // mapear o rest controller
public class ProdutoRestController {

	@Autowired
	private IProdutoService produtoService;

	// metodo index para listar produto
	@GetMapping("/produtos") // mapear url
	public List<Produto> index() {
		return produtoService.findAll();
	}

// produto com paginacao
	@GetMapping("/produtos/page/{page}") // mapear url
	public Page<Produto> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return produtoService.findAll(pageable);
	}

	// @Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/produtos/{id}")
	public ResponseEntity<?> show(@PathVariable long id) {
		Produto produto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			produto = produtoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error ao realizar a consulta na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (produto == null) {// id.toString().concat
			response.put("mensaje", "O produto ID:".concat(("Não existe na base de dados!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	// salvar

	// adcionado regras de permições
	// @Secured({ "ROLE_ADMIN" })
	@PostMapping("/produtos")
	public ResponseEntity<?> create(@Valid @RequestBody Produto produto, BindingResult result) {
		Produto produtoNew = null;
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
			produtoNew = produtoService.save(produto);
		} catch (DataAccessException e) {

			response.put("mensaje", "Erro ao inserir os dados na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Produto adcionado com sucesso!");
		response.put("produto", produtoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// actualizar
	// adcionado regras de permições
	// @Secured("ROLE_ADMIN")
	@PutMapping("/produtos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Produto produto, BindingResult result, @PathVariable Long id) {
		Produto produtoActual = produtoService.findById(id);
		Produto produtoUpdate = null;
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
		if (produtoActual == null) {// id.toString().concat
			response.put("mensaje", "Erro: não foi posivel editar O produto ID:"
					.concat(id.toString().concat("Não existe na base de dados!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			produtoActual.setNome(produto.getNome());
			produtoActual.setPreco(produto.getPreco());
			produtoActual.setCodigo(produto.getCodigo());
			produtoActual.setQuantidadeEstoque(produto.getQuantidadeEstoque());
			produtoActual.setDataCriacao(produto.getDataCriacao());

			produtoUpdate = produtoService.save(produtoActual);
		} catch (DataAccessException e) {

			response.put("mensaje", "Erro ao atualizar o produto na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Produto actualizado com sucesso!");
		response.put("produto", produtoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	// delecte
	// adcionado regras de permições
	// @Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			produtoService.delete(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Erro ao eliminar o produto na base de dados");
			response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "O produto foi eliminado sucesso!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}

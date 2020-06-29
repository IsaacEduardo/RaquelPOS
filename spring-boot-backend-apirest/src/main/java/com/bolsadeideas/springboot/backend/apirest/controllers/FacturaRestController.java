package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class FacturaRestController {
	@Autowired
	private IClienteService clienteService;

	// mostrar detalhes da facturas por id
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/facturas/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Factura show(@PathVariable Long id) {
		return clienteService.findFacturaById(id);
	}

	// eliminar factura
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/facturas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.deleteFacturaById(id);
	}

	// filtro de produtos
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/facturas/filtrar-produtos/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> filtrarProdutos(@PathVariable String term) {
		return clienteService.findProdutoByNome(term);
	}

	/*
	 * @Secured({ "ROLE_ADMIN" })
	 * 
	 * @PostMapping("/facturas")
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public Factura crear(@RequestBody Factura
	 * factura) { // return clienteService.saveFactura(factura); return
	 * clienteService.salvarFaturaStoque(factura); }
	 * 
	 */
	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/facturas")
	@ResponseStatus(HttpStatus.CREATED)
	public Factura crear(@RequestBody Factura factura) {
		// return clienteService.saveFactura(factura);
		return clienteService.salvarFaturaStoque(factura);

	}

}

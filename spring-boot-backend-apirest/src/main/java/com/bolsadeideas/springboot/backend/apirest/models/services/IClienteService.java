package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Region;

public interface IClienteService {
	// listar todos
	public List<Cliente> findAll();

	// paginacao
	public Page<Cliente> findAll(Pageable pageable);

	public Cliente save(Cliente cliente);

	public void delete(Long id);

	public Cliente findById(Long id);

	public List<Region> findAllRegiones();

	// obter a factura por id
	public Factura findFacturaById(Long id);

	// salvar factura

	public Factura saveFactura(Factura fatura);
	//public Factura salvarFaturaStoque(Factura factura); 

	// delectar factura
	public void deleteFacturaById(Long id);
	
	// buscar produto por nome
	
	public List<Produto> findProdutoByNome(String term);

	public Factura salvarFaturaStoque(Factura factura);

}

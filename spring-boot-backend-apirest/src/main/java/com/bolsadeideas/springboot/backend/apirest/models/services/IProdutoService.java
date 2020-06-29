package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;

public interface IProdutoService {
	
	// listar todos
		public List<Produto> findAll();
		// paginacao
		public Page<Produto> findAll(Pageable pageable);

		public Produto save(Produto produto);

		public void delete(Long id);

		public Produto findById(Long id);

}

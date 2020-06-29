package com.bolsadeideas.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;

public interface IProdutoDao extends JpaRepository<Produto, Long> {

	@Query("select p from Produto p where p.nome like %?1%")
	public List<Produto> findByNome(String term);

	public List<Produto> findByNomeContainingIgnoreCase(String term);

	public List<Produto> findByNomeStartingWithIgnoreCase(String term);
}

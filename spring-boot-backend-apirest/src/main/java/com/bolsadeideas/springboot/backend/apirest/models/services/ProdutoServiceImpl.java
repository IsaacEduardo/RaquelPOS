package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IProdutoDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;

@Service
public class ProdutoServiceImpl implements IProdutoService {

	@Autowired
	private IProdutoDao produtoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Produto> findAll() {
		return (List<Produto>) produtoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Produto> findAll(Pageable pageable) {
		return produtoDao.findAll(pageable);
	}

	@Override
	@Transactional
	public Produto save(Produto produto) {
		return produtoDao.save(produto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		produtoDao.deleteById(id);

	}

	@Override
	public Produto findById(Long id) {
		return produtoDao.findById(id).orElse(null);
	}

}

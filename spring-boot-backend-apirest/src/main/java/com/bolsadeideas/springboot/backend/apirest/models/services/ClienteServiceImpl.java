package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.IFacturaDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.IProdutoDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ItemFactura;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Produto;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Region;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	@Autowired
	private IFacturaDao facturaDao;
	@Autowired
	private IProdutoDao produtoDao;


	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {

		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {

		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {

		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {

		return clienteDao.findAllRegiones();
	}

	// referente a factura

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura fatura) {

		return facturaDao.save(fatura);

	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		facturaDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Produto> findProdutoByNome(String term) {

		return produtoDao.findByNomeContainingIgnoreCase(term);
	}

	@Override
	@Transactional
	public Factura salvarFaturaStoque(Factura factura) {
		try {
			facturaDao.save(factura);
			System.out.println("Salvou a fatura");
			// dando baixa no stoque
			for (ItemFactura item : factura.getItens()) {
				System.out.println("Entrou no for");
				Produto produto = produtoDao.findById(item.getProduto().getId()).get();
				System.out.println("Encontrou o produto");
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
				produtoDao.save(produto);
				System.out.println("Finalmente funcionou Funcionou");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao salvar a factura");
		}
		return factura;

	}

}

package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.backend.apirest.models.dao.ItemFacturaDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ItemFactura;

@Service
public class ItemFacturaServiceImp implements ItemFacturaService {

	@Autowired
	private ItemFacturaDao itemFacturaDao;

	@Override
	public List<ItemFactura> findByFactura(Factura factura) {
		return itemFacturaDao.findByFactura(factura);
	}

}

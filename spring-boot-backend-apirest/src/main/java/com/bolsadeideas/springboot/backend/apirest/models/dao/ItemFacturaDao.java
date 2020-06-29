package com.bolsadeideas.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ItemFactura;

public interface ItemFacturaDao extends JpaRepository<ItemFactura, Long>{
	
	List<ItemFactura> findByFactura(Factura factura);
}

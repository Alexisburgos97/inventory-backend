package com.aburgos.inventory.services;

import org.springframework.http.ResponseEntity;

import com.aburgos.inventory.model.Product;
import com.aburgos.inventory.response.ProductResponseRest;


public interface IProductService{
	
	public ResponseEntity<ProductResponseRest> search();
	
	public ResponseEntity<ProductResponseRest> searchById(Long id);
	
	public ResponseEntity<ProductResponseRest> searchByName(String name);
	
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
	
	public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id);
	
	public ResponseEntity<ProductResponseRest> delete(Long id);

}

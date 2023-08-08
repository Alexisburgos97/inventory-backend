package com.aburgos.inventory.services;

import org.springframework.http.ResponseEntity;

import com.aburgos.inventory.model.Category;
import com.aburgos.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseRest> search();
	
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	
	public ResponseEntity<CategoryResponseRest> save(Category category);

}

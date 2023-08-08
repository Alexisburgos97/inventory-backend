package com.aburgos.inventory.services;

import org.springframework.http.ResponseEntity;

import com.aburgos.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseRest> search();

}

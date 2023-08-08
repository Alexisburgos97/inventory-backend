package com.aburgos.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aburgos.inventory.model.Category;
import com.aburgos.inventory.response.CategoryResponseRest;
import com.aburgos.inventory.services.ICategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
	
	@Autowired
	private ICategoryService categoryService;

	/**
	 * get all categories
	 * @return
	 */
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories(){
		
		ResponseEntity<CategoryResponseRest> response = categoryService.search();
		
		return response;
	}
	
	/**
	 * get categories by id
	 * @param id
	 * @return
	 */
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id){
		
		ResponseEntity<CategoryResponseRest> response = categoryService.searchById(id);
		
		return response;
	}
	
	/**
	 * save category
	 * @param Category
	 * @return
	 */
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category){
	
		ResponseEntity<CategoryResponseRest> response = categoryService.save(category);
		
		return response;
	}
	
	/**
	 * update category
	 * @param Category
	 * @param id
	 * @return
	 */
	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category, @PathVariable Long id){
	
		ResponseEntity<CategoryResponseRest> response = categoryService.update(category, id);
		
		return response;
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id){
	
		ResponseEntity<CategoryResponseRest> response = categoryService.delete(id);
		
		return response;
	}
	
}

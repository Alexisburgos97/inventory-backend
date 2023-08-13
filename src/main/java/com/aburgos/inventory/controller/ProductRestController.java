package com.aburgos.inventory.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aburgos.inventory.model.Category;
import com.aburgos.inventory.model.Product;
import com.aburgos.inventory.response.CategoryResponseRest;
import com.aburgos.inventory.response.ProductResponseRest;
import com.aburgos.inventory.services.IProductService;
import com.aburgos.inventory.util.Util;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
	
	@Autowired
	private IProductService productService;
	
    private static final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

    /**
	 * get products by id
	 * @param id
	 * @return
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> searchCategoriesById(@PathVariable Long id){
		
		ResponseEntity<ProductResponseRest> response = productService.searchById(id);
		
		return response;
	}
    
    /**
	 * save product
	 * @param Product
	 * @param categoryId
	 * @return
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductResponseRest> save(
			@RequestParam("picture") MultipartFile picture, @RequestParam("name") String name,
			@RequestParam("price") int price, @RequestParam("account") int account,
			@RequestParam("categoryId") Long categoryId) throws IOException {
		
		Product product = new Product();
		product.setName(name);
		product.setAccount(account);
		product.setPrice(price);
		
		product.setPicture(Util.compressZLib(picture.getBytes()));
		
		ResponseEntity<ProductResponseRest> response = productService.save(product, categoryId);
		
		return response;
	}
	
}

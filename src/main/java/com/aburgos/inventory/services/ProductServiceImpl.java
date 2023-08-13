package com.aburgos.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aburgos.inventory.dao.ICategoryDao;
import com.aburgos.inventory.dao.IProductDao;
import com.aburgos.inventory.model.Category;
import com.aburgos.inventory.model.Product;
import com.aburgos.inventory.response.CategoryResponseRest;
import com.aburgos.inventory.response.ProductResponseRest;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private ICategoryDao categoryDao;

	@Override
	public ResponseEntity<ProductResponseRest> search() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if( category.isPresent() ) {
				
				product.setCategory(category.get());
				
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Categoría no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND); 
			}
			
			Product productSaved = productDao.save(product);
			
			if( productSaved != null ) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto guardado");
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST); 
			}
			
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al guardar el producto");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK); 
	}

	@Override
	public ResponseEntity<ProductResponseRest> update(Product product, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductResponseRest> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

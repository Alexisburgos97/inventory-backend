package com.aburgos.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aburgos.inventory.dao.ICategoryDao;
import com.aburgos.inventory.dao.IProductDao;
import com.aburgos.inventory.model.Category;
import com.aburgos.inventory.model.Product;
import com.aburgos.inventory.response.CategoryResponseRest;
import com.aburgos.inventory.response.ProductResponseRest;
import com.aburgos.inventory.util.Util;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private ICategoryDao categoryDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> search() {
		
		ProductResponseRest response = new ProductResponseRest();
		
		try {
			
			List<Product> products = (List<Product>) productDao.findAll();
			
			response.getProduct().setProducts(products);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK); 
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long id) {

		ProductResponseRest response = new ProductResponseRest();
		List<Product> list  = new ArrayList<>();
		
		try {
			
			Optional<Product> product = productDao.findById(id);
			
			if( product.isPresent() ) {
				
				byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
				
				product.get().setPicture(imageDescompressed);
				
				list.add(product.get());
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
			
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Producto no encontrada");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND); 
			}
			
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al consultar por id");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK); 
	}

	@Override
	@Transactional
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
	@Transactional
	public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list  = new ArrayList<>();
		
		try {
			
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if( category.isPresent() ) {
				
				product.setCategory(category.get());
				
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Categoría no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND); 
			}
			
			Optional<Product> productSearch = productDao.findById(id);
			
			if( productSearch.isPresent() ) {
				
				productSearch.get().setAccount(product.getAccount());
				productSearch.get().setName(product.getName());
				productSearch.get().setPrice(product.getPrice());
				productSearch.get().setPicture(product.getPicture());
				productSearch.get().setCategory(product.getCategory());
				
				Product productToUpdate = productDao.save(productSearch.get());
				
				if(productToUpdate != null) {
					list.add(productToUpdate);
					response.getProduct().setProducts(list);
					response.setMetadata("Respuesta ok", "00", "Producto actualizado");
				}
				else {
					response.setMetadata("Respuesta nok", "-1", "Producto no actualizada");
					return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST); 
				}
			
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Producto no actualizada");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND); 
			}
			
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al actualizar el Producto");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK); 
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> delete(Long id) {

		ProductResponseRest response = new ProductResponseRest();
		
		try {
			
			productDao.deleteById(id);
			
			response.setMetadata("Respuesta ok", "00", "Producto eliminada");
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al eliminar el producto");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK); 
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list  = new ArrayList<>();
		List<Product> listAux  = new ArrayList<>();
		
		try {
			
			listAux = productDao.findByNameContainingIgnoreCase(name);
			
			if( listAux.size() > 0 ) {
				
				listAux.stream().forEach( product -> {
					
					byte[] imageDescompressed = Util.decompressZLib(product.getPicture());
					
					product.setPicture(imageDescompressed);
					
					list.add(product);
					
				});
				
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
			
			}
			else {
				response.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND); 
			}
			
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al consultar por nombre");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK); 
	}

}

package com.aburgos.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.aburgos.inventory.model.Product;

public interface IProductDao extends CrudRepository<Product, Long>{

}

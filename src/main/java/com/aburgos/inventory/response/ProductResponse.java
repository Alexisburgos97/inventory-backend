package com.aburgos.inventory.response;

import java.util.List;

import com.aburgos.inventory.model.Product;

import lombok.Data;

@Data
public class ProductResponse {

	private List<Product> products;
	
}

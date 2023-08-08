package com.aburgos.inventory.response;

import java.util.List;

import com.aburgos.inventory.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {
	
	private List<Category> category;

}

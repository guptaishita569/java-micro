package com.ecom.services.admin.category;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createcategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}

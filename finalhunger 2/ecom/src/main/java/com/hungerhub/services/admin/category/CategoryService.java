package com.hungerhub.services.admin.category;

import com.hungerhub.dto.CategoryDto;
import com.hungerhub.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createcategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}

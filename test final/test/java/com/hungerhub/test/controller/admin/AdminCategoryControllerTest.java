package com.hungerhub.test.controller.admin;

import com.hungerhub.controller.admin.AdminCategoryController;
import com.hungerhub.dto.CategoryDto;
import com.hungerhub.entity.Category;
import com.hungerhub.services.admin.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminCategoryControllerTest {

    @InjectMocks
    private AdminCategoryController adminCategoryController;
    @Mock
    private CategoryService categoryService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto();
        Category category = new Category();
        when(categoryService.createcategory(categoryDto)).thenReturn(category);
        ResponseEntity<Category> responseEntity = adminCategoryController.createCategory(categoryDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        when(categoryService.getAllCategories()).thenReturn(categories);
        ResponseEntity<List<Category>> responseEntity = adminCategoryController.getAllCategories();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categories, responseEntity.getBody());
    }
}

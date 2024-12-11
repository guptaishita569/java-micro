//package com.hungerHub.ecom;
//
//public class CategoryServiceImplTest {
//
//}
package com.hungerHub.ecom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.services.admin.category.CategoryServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("TestCategory");
        categoryDto.setDescription("Test Description");

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category createdCategory = categoryService.createcategory(categoryDto);
        Assertions.assertEquals(categoryDto.getName(), createdCategory.getName());
        Assertions.assertEquals(categoryDto.getDescription(), createdCategory.getDescription());
    }

    @Test
    public void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("TestCategory1");
        category1.setDescription("Test Description 1");

        Category category2 = new Category();
        category2.setName("TestCategory2");
        category2.setDescription("Test Description 2");

        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> retrievedCategories = categoryService.getAllCategories();

        Assertions.assertEquals(categories.size(), retrievedCategories.size());
        Assertions.assertEquals(categories.get(0), retrievedCategories.get(0));
        Assertions.assertEquals(categories.get(1), retrievedCategories.get(1));
    }
}

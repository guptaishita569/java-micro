package com.ecom.controller.admin;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.entity.Coupon;
import com.ecom.exceptions.ValidationException;
import com.ecom.services.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/admin")
@RequiredArgsConstructor
public class AdminCategoryController {
@Autowired
    private CategoryService categoryService;
//Category
    @PostMapping("category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        try {
        	Category category = categoryService.createcategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}

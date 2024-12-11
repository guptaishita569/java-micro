package com.hungerhub.services.customer;

import java.util.List;

import com.hungerhub.dto.ProductDetailDto;
import com.hungerhub.dto.ProductDto;

public interface CustomerProductService {

    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> getAllProducts();

    ProductDetailDto getProductDetailById(Long productId);
}

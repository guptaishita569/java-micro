package com.hungerhub.services.customer.review;

import java.io.IOException;

import com.hungerhub.dto.OrderedProductsResponseDto;
import com.hungerhub.dto.ReviewDto;

public interface ReviewService {

    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}

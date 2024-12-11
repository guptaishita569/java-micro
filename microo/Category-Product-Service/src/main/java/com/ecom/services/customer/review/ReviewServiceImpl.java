package com.ecom.services.customer.review;

import com.ecom.dto.*;
import com.ecom.entity.Product;
import com.ecom.entity.Review;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.ReviewRepository;
import com.ecom.services.feign.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
@Autowired
    private ProductRepository productRepository;
@Autowired
    private ReviewRepository reviewRepository;
@Autowired
    private AuthService authService;

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(reviewDto.getProductId());
        ResponseEntity<UserDto> userDto = authService.getUserById(reviewDto.getUserId());
        if (optionalProduct.isPresent() && userDto.getStatusCode().is2xxSuccessful()) {
            Review review = new Review();
            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setUserId(Objects.requireNonNull(userDto.getBody()).getId());
            review.setUsername(userDto.getBody().getName());
            review.setProduct(optionalProduct.get());
            review.setImg(reviewDto.getImg().getBytes());
            return reviewRepository.save(review).getDto();
        }
        return null;
    }
}

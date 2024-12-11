package com.hungerhub.test.controller.customer;

import com.hungerhub.controller.customer.ReviewController;
import com.hungerhub.dto.OrderedProductsResponseDto;
import com.hungerhub.dto.ReviewDto;
import com.hungerhub.services.customer.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {
    @InjectMocks
    private ReviewController reviewController;
    @Mock
    private ReviewService reviewService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetOrderedProductsDetailsByOrderId() {
        Long orderId = 1L;
        OrderedProductsResponseDto orderedProductsResponseDto = new OrderedProductsResponseDto();
        when(reviewService.getOrderedProductsDetailsByOrderId(orderId)).thenReturn(orderedProductsResponseDto);
        ResponseEntity<OrderedProductsResponseDto> result = reviewController.getOrderedProductsDetailsByOrderId(orderId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderedProductsResponseDto, result.getBody());
        verify(reviewService, times(1)).getOrderedProductsDetailsByOrderId(orderId);
    }

    @Test
    void testGiveReviewSuccess() throws IOException {
        ReviewDto reviewDto = new ReviewDto();
        when(reviewService.giveReview(reviewDto)).thenReturn(reviewDto);
        ResponseEntity<?> result = reviewController.giveReview(reviewDto);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(reviewDto, result.getBody());
        verify(reviewService, times(1)).giveReview(reviewDto);
    }

    @Test
    void testGiveReviewFailure() throws IOException {
        ReviewDto reviewDto = new ReviewDto();
        when(reviewService.giveReview(reviewDto)).thenReturn(null);
        ResponseEntity<?> result = reviewController.giveReview(reviewDto);
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Something went wrong", result.getBody());
        verify(reviewService, times(1)).giveReview(reviewDto);
    }
}

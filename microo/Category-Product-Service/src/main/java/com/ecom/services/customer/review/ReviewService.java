package com.ecom.services.customer.review;

import com.ecom.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {


    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;

}

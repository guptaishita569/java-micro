package com.hungerhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDto {

    private ProductDto productDto;

    public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public List<ReviewDto> getReviewDtoList() {
		return reviewDtoList;
	}

	public void setReviewDtoList(List<ReviewDto> reviewDtoList) {
		this.reviewDtoList = reviewDtoList;
	}

	public List<FAQDto> getFaqDtoList() {
		return faqDtoList;
	}

	public void setFaqDtoList(List<FAQDto> faqDtoList) {
		this.faqDtoList = faqDtoList;
	}

	private List<ReviewDto> reviewDtoList;

    private List<FAQDto> faqDtoList;
}

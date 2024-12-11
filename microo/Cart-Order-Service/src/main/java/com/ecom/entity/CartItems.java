package com.ecom.entity;

import com.ecom.dto.CartItemsDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private Long quantity;

    private Long productId;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

//    public CartItemsDto getCartDto() {
//        CartItemsDto cartItemsDto = new CartItemsDto();
//        cartItemsDto.setId(id);
//        cartItemsDto.setPrice(price);
//        cartItemsDto.setProductId(productId);
//        cartItemsDto.setQuantity(quantity);
//        cartItemsDto.setUserId(userId);
////        cartItemsDto.setProductName(product.getName());
////        cartItemsDto.setReturnedImg(product.getImg());
//        return cartItemsDto;
//    }
}

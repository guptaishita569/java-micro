package com.hungerhub.services.customer.wishlist;

import java.util.List;

import com.hungerhub.dto.WishlistDto;

public interface WishlistService {

    WishlistDto addProductToWishlist(WishlistDto wishlistDto);

    List<WishlistDto> getWishlistByUserId(Long userId);
}

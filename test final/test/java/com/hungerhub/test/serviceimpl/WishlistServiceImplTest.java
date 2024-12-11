package com.hungerhub.test.serviceimpl;

import com.hungerhub.dto.WishlistDto;
import com.hungerhub.entity.Product;
import com.hungerhub.entity.User;
import com.hungerhub.entity.Wishlist;
import com.hungerhub.repository.ProductRepository;
import com.hungerhub.repository.UserRepository;
import com.hungerhub.repository.WishlistRepository;
import com.hungerhub.services.customer.wishlist.WishlistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WishlistServiceImplTest {
    @InjectMocks
    private WishlistServiceImpl wishlistService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProductToWishlist_Success() {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUserId(1L);
        wishlistDto.setProductId(1L);
        Product product = new Product();
        product.setId(1L);
        User user = new User();
        user.setId(1L);
        Wishlist wishlist = new Wishlist();
        wishlist.setProduct(product);
        wishlist.setUser(user);
        when(productRepository.findById(wishlistDto.getProductId())).thenReturn(Optional.of(product));
        when(userRepository.findById(wishlistDto.getUserId())).thenReturn(Optional.of(user));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);
        WishlistDto result = wishlistService.addProductToWishlist(wishlistDto);
        assertNotNull(result);
    }

    @Test
    public void testgetWishlistByUserId(){
        Long userId = 1L;
        Wishlist wishlist1 = new Wishlist();
        wishlist1.setId(1L);
        Wishlist wishlist2 = new Wishlist();
        wishlist2.setId(2L);
        User user = new User();
        user.setId(1L);
        Product product = new Product();
        product.setId(1L);
        wishlist1.setUser(user);
        wishlist2.setUser(user);
        wishlist2.setProduct(product);
        wishlist1.setProduct(product);
        List<Wishlist> wishlistList = List.of(wishlist1, wishlist2);
        when(wishlistRepository.findAllByUserId(userId)).thenReturn(wishlistList);
        List<WishlistDto> result = wishlistService.getWishlistByUserId(userId);
        assertNotNull(result);
        assertEquals(wishlistList.size(), result.size());
    }

}

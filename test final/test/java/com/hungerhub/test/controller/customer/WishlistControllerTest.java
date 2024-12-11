package com.hungerhub.test.controller.customer;

import com.hungerhub.controller.customer.WishlistController;
import com.hungerhub.dto.WishlistDto;
import com.hungerhub.services.customer.wishlist.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class WishlistControllerTest {
    @InjectMocks
    private WishlistController wishlistController;
    @Mock
    private WishlistService wishlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testAddProductToWishlistSuccess() {
        WishlistDto wishlistDto = new WishlistDto();
        when(wishlistService.addProductToWishlist(wishlistDto)).thenReturn(wishlistDto);
        ResponseEntity<?> result = wishlistController.addProductToWishlist(wishlistDto);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(wishlistDto, result.getBody());
        verify(wishlistService, times(1)).addProductToWishlist(wishlistDto);
    }

    @Test
    void testAddProductToWishlistFailure() {
        WishlistDto wishlistDto = new WishlistDto();
        when(wishlistService.addProductToWishlist(wishlistDto)).thenReturn(null);
        ResponseEntity<?> result = wishlistController.addProductToWishlist(wishlistDto);
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Something went wrong", result.getBody());
        verify(wishlistService, times(1)).addProductToWishlist(wishlistDto);
    }

    @Test
    void testGetWishlistByUserId() {
        Long userId = 1L;
        List<WishlistDto> wishlistDtoList = new ArrayList<>();
        when(wishlistService.getWishlistByUserId(userId)).thenReturn(wishlistDtoList);
        ResponseEntity<List<WishlistDto>> result = wishlistController.getWishlistByUserId(userId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(wishlistDtoList, result.getBody());
        verify(wishlistService, times(1)).getWishlistByUserId(userId);
    }
}

package com.ecom.services.customer.cart;

import com.ecom.dto.*;
import com.ecom.entity.CartItems;
import com.ecom.entity.Order;
import com.ecom.enums.OrderStatus;
import com.ecom.exceptions.ValidationException;
import com.ecom.repository.CartItemsRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.services.feign.AuthService;
import com.ecom.services.feign.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProductService productService;


    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            ResponseEntity<ProductDto> productDto = productService.getProductById(addProductInCartDto.getProductId());
            ResponseEntity<UserDto> userDto = authService.getUserById(addProductInCartDto.getUserId());

            if (productDto.getStatusCode().is2xxSuccessful() && userDto.getStatusCode().is2xxSuccessful()) {
                CartItems cart = new CartItems();
                cart.setProductId(Objects.requireNonNull(productDto.getBody()).getId());
                cart.setPrice(productDto.getBody().getPrice());
                cart.setQuantity(1L);
                cart.setUserId(Objects.requireNonNull(userDto.getBody()).getId());
                cart.setOrder(activeOrder);

                cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart.getId());

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(this::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if (activeOrder.getCouponId() != null) {
            ResponseEntity<CouponDto> couponDto = productService.findCouponById(activeOrder.getCouponId());
            orderDto.setCouponName(Objects.requireNonNull(couponDto.getBody()).getName());
        }
        return orderDto;
    }

    public CartItemsDto getCartDto(CartItems cartItem) {
        CartItemsDto cartItemsDto = new CartItemsDto();
        cartItemsDto.setId(cartItem.getId());
        cartItemsDto.setPrice(cartItem.getPrice());
        cartItemsDto.setProductId(cartItem.getProductId());
        cartItemsDto.setQuantity(cartItem.getQuantity());
        cartItemsDto.setUserId(cartItem.getUserId());
        ResponseEntity<ProductDto> product = productService.getProductById(cartItem.getProductId());
        cartItemsDto.setProductName(Objects.requireNonNull(product.getBody()).getName());
        cartItemsDto.setReturnedImg(product.getBody().getByteImg());
        return cartItemsDto;
    }

    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        ResponseEntity<CouponDto> couponDto = productService.findCouponByCode(code);

        if (couponIsExpired(Objects.requireNonNull(couponDto.getBody()))) {
            throw new ValidationException("Coupon has expired.");
        }

        double discountAmount = ((Objects.requireNonNull(couponDto.getBody()).getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCouponId(couponDto.getBody().getId());
        

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(CouponDto couponDto) {
        Date currentdate = new Date();
        Date expirationDate = couponDto.getExpirationDate();
        return expirationDate != null && currentdate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        ResponseEntity<ProductDto> productDto = productService.getProductById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (productDto.getStatusCode().is2xxSuccessful() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();

            activeOrder.setAmount(activeOrder.getAmount() + Objects.requireNonNull(productDto.getBody()).getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + Objects.requireNonNull(productDto.getBody()).getPrice());

            cartItem.setQuantity(cartItem.getQuantity() + 1);
            if (activeOrder.getCouponId() != null) {
                ResponseEntity<CouponDto> couponDto = productService.findCouponById(activeOrder.getCouponId());
                double discountAmount = ((Objects.requireNonNull(couponDto.getBody()).getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        ResponseEntity<ProductDto> productDto = productService.getProductById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (productDto.getStatusCode().is2xxSuccessful() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();

            activeOrder.setAmount(activeOrder.getAmount() - Objects.requireNonNull(productDto.getBody()).getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - Objects.requireNonNull(productDto.getBody()).getPrice());

            cartItem.setQuantity(cartItem.getQuantity() - 1);
            if (activeOrder.getCouponId() != null) {
                ResponseEntity<CouponDto> couponDto = productService.findCouponById(activeOrder.getCouponId());
                double discountAmount = ((Objects.requireNonNull(couponDto.getBody()).getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        ResponseEntity<UserDto> userDto = authService.getUserById(placeOrderDto.getUserId());
        if (userDto.getStatusCode().is2xxSuccessful()) {
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUserId(Objects.requireNonNull(userDto.getBody()).getId());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId) {
        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed, OrderStatus.Shipped,
                OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        return optionalOrder.map(Order::getOrderDto).orElse(null);
    }

    @Override
    public OrderDto createNewOrder(Long userId) {
        try {
            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUserId(userId);
            order.setOrderStatus(OrderStatus.Pending);
            return orderRepository.save(order).getOrderDto();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(Order::getOrderDto).orElse(null);
    }

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        OrderedProductsResponseDto orderedProductsResponseDto = new OrderedProductsResponseDto();
        if (optionalOrder.isPresent()) {
            orderedProductsResponseDto.setOrderAmount(optionalOrder.get().getAmount());
            List<ProductDto> productDtoList = new ArrayList<>();
            for (CartItems cartItems : optionalOrder.get().getCartItems()) {
                ResponseEntity<ProductDto> productDto = productService.getProductById(cartItems.getProductId());
                if (productDto.getStatusCode().is2xxSuccessful()) {
                    ProductDto newProductDto = new ProductDto();
                    newProductDto.setId(Objects.requireNonNull(productDto.getBody()).getId());
                    newProductDto.setName(productDto.getBody().getName());
                    newProductDto.setPrice(productDto.getBody().getPrice());
                    newProductDto.setQuantity(productDto.getBody().getQuantity());
                    newProductDto.setByteImg(productDto.getBody().getByteImg());
                    productDtoList.add(newProductDto);
                }
            }
            orderedProductsResponseDto.setProductDtoList(productDtoList);
        }
        return orderedProductsResponseDto;
    }


}

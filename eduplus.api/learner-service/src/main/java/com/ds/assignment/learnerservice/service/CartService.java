package com.ds.assignment.learnerservice.service;

import com.ds.assignment.learnerservice.dto.CartRequest;
import com.ds.assignment.learnerservice.dto.CartResponse;
import com.ds.assignment.learnerservice.model.Cart;
import com.ds.assignment.learnerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service

@Slf4j

public class CartService {
    @Autowired
    private  CartRepository cartRepository;

    public void createCart(CartRequest cartRequest) throws IOException {
        List<String> courseId = cartRequest.getCourse_id();

        Cart cart = Cart.builder()
                .course_id(courseId)
                .account_id(cartRequest.getAccount_id())
                .build();

        cartRepository.save(cart);
        log.info("Course {} is added to cart successfully.", cart.getCourse_id());
    }

    public CartResponse getCartById(String accountId) {
        Cart cart = cartRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found with id: " + accountId));
        return mapToCartResponse(cart);
    }

    private CartResponse mapToCartResponse(Cart cart) {
        return CartResponse.builder()
                .course_id(cart.getCourse_id())
                .account_id(cart.getAccount_id())
                .build();
    }

    public void deleteCoursesByAccountId(String accountId, String courseId) {
            cartRepository.deleteByAccountIdAndCourseId(accountId, courseId);
            log.info("Course {} associated with account {} is deleted", courseId, accountId);
        }


}

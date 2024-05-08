package com.ds.assignment.learnerservice.controller;


import com.ds.assignment.learnerservice.dto.CartRequest;
import com.ds.assignment.learnerservice.dto.CartResponse;
import com.ds.assignment.learnerservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public void createCart(@RequestBody CartRequest cartRequest) throws IOException {

        cartService.createCart(cartRequest);
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable String accountId) {
        CartResponse cartResponse = cartService.getCartById(accountId);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteCourses(@PathVariable String accountId, @RequestBody String courseId) {
        cartService.deleteCoursesByAccountId(accountId, courseId);
        return ResponseEntity.ok("Courses deleted successfully");
    }

}

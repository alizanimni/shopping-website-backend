package com.example.security.controller;

import com.example.security.model.AddItemRequest;
import com.example.security.model.CustomUser;
import com.example.security.model.Order;
import com.example.security.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cart")
public class CartController {

@Autowired
    private CartService cartService;

@GetMapping
public ResponseEntity<Order> getCart(){
try{
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    String username = user.getUsername();
    System.out.println(username);
    Order cart = cartService.getCart(username);
     return ResponseEntity.ok(cart);
}catch (Exception e){
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
}


    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(@RequestBody AddItemRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = ((User) authentication.getPrincipal()).getUsername();

            cartService.addItemToCart(username, request);

            return ResponseEntity.ok("Item added successfully");
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/decrease-item")
    public ResponseEntity<String> decreaseItemInCart(@RequestBody AddItemRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = ((User) authentication.getPrincipal()).getUsername();

            cartService.decreaseItemInCart(username, request);

            return ResponseEntity.ok("Item deleted successfully");
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> closeOrder(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = ((User) authentication.getPrincipal()).getUsername();

            cartService.closeOrder(username);

            return ResponseEntity.ok("Order closed");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getUserAllOrders(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = ((User) authentication.getPrincipal()).getUsername();

           List<Order> allOrders =  cartService.getAllUserOrders(username);

            return ResponseEntity.ok(allOrders);
        } catch (Exception e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }



}

package com.example.security.service;

import com.example.security.model.AddItemRequest;
import com.example.security.model.Order;
import com.example.security.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Order getCart(String username){
      return cartRepository.getCart(username);
    }

//    public String createNewCart(String username,String address){
//        return cartRepository.createNewCart(username,address);
//    }

    public String addItemToCart(String username, AddItemRequest request) {
        Order cart = getCart(username);
        if (cart == null) {
           cartRepository.createNewCart(username,"fgs");
            cart = getCart(username);
        }
        Double price= request.getPrice()*request.getQuantity();
        //  cartRepository.addItemToCart(request.getItemId(), cart.getId(), request.getQuantity(),price);
        boolean exists = cartRepository.isItemExistOnCart(request.getItemId(), cart.getId());

        if (exists) {
            cartRepository.updateItemInCart(request.getItemId(), cart.getId(), request.getQuantity(), price);
            return "Item updated in cart";
        } else {
            cartRepository.addItemToCart(request.getItemId(), cart.getId(), request.getQuantity(), price);
            return "Item added to cart";
        }
    }

    public String decreaseItemInCart(String username, AddItemRequest request) {
        Order cart = getCart(username);
        if (cart == null) {
            throw new IllegalStateException("No open cart found");
        }
        Double price= request.getPrice()*request.getQuantity();
            cartRepository.decreaseItemInCart(request.getItemId(), cart.getId(), request.getQuantity(), price);
            return "Item decreased";

    }


    public String closeOrder(String username) {
        Order cart = getCart(username);
        if (cart == null) {
            throw new IllegalStateException("No open cart found");
        }

        cartRepository.closeOrder(username,cart.getId());
        return "Order closed";

    }

    public List<Order> getAllUserOrders(String username){
        return  cartRepository.getAllUserOrders(username);
    }


}

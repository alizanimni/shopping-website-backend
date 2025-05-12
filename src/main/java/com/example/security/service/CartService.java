package com.example.security.service;

import com.example.security.model.AddItemRequest;
import com.example.security.model.CustomUser;
import com.example.security.model.Order;
import com.example.security.model.OrderedItem;
import com.example.security.repository.CartRepository;
import com.example.security.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public Order getCart(String username){
      return cartRepository.getCart(username);
    }

//    public String createNewCart(String username,String address){
//        return cartRepository.createNewCart(username,address);
//    }

    public String addItemToCart(String username, AddItemRequest request) {
        System.out.println("add");
        Order cart = getCart(username);
        if (cart == null) {
           CustomUser user = userService.getUserByUsername(username);
           cartRepository.createNewCart(username,user.getAddress());
            cart = getCart(username);
        }
        Double price= request.getPrice()*request.getQuantity();
        boolean exists = cartRepository.isItemExistOnCart(request.getItemId(), cart.getId());

        if (exists) {
            cartRepository.updateItemInCart(request.getItemId(), cart.getId(), request.getQuantity(), price);
            cartRepository.updateOrderTotalPrice(cart.getId(),request.getPrice());
            return "Item updated in cart";
        } else {
            cartRepository.addItemToCart(request.getItemId(), cart.getId(), request.getQuantity(), price);
            cartRepository.updateOrderTotalPrice(cart.getId(),request.getPrice());
            return "Item added to cart";
        }


    }

    public void decreaseItemInCart(String username, AddItemRequest request) {
        System.out.println("down");
        int orderId = cartRepository.getCartIdByUser(username);

        int currentQuantity = cartRepository.getQuantityForItem(orderId, request.getItemId());

        if (currentQuantity <= request.getQuantity()) {
            cartRepository.deleteItemFromCart(orderId,request.getPrice(), request.getItemId());
        } else {
            cartRepository.decreaseItemQuantityAndPrice(orderId, request.getItemId(), request.getQuantity(), request.getPrice());
        }

        cartRepository.updateOrderTotalPrice(orderId, -request.getPrice());
    }




    public String closeOrder(String username) {
        System.out.println("hello");
        Order cart = getCart(username);
        if (cart == null) {
            throw new IllegalStateException("No open cart found");
        }
        System.out.println(cart);
        System.out.println(cart.getItemsOnOrder());
        String result = itemService.updateItemsAfterCartCheckout(cart.getItemsOnOrder());
        cartRepository.closeOrder(username,cart.getId());
        return "Order closed" + result;

    }


    public List<Order> getAllUserOrders(String username){
        return  cartRepository.getAllUserOrders(username);
    }


}

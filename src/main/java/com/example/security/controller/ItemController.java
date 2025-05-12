package com.example.security.controller;

import com.example.security.model.CustomUser;
import com.example.security.model.Item;
import com.example.security.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems(){
        System.out.println(" getAllItems called!");
        try{
            List<Item> allItems = itemService.getAllItems();
            return ResponseEntity.ok(allItems);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable int id) {
        try {
            Item item = itemService.getItem(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item with ID " + id + " not found");
            }
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the item.");
        }
    }

    @PostMapping("/add-to-favorite/{id}")
    public ResponseEntity<String> addItemToFavoriteList(@PathVariable int id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();

            return itemService.addItemToFavoriteList(username, id);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity<?> getAllFavoriteItems(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            List<Integer> itemsList = itemService.getAllFavoriteItems(username);
            return ResponseEntity.ok(itemsList);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/favorites/{itemId}")
    public ResponseEntity<String> removeFavorite(@PathVariable int itemId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = ((User) auth.getPrincipal()).getUsername();

            itemService.removeItemFromFavorites(username, itemId); // מחזירה void

            return ResponseEntity.ok("Item removed from favorites");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }


}

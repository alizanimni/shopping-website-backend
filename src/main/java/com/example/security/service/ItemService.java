package com.example.security.service;

import com.example.security.model.Item;
import com.example.security.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(){
        return itemRepository.getAllItems();
    }

    public Item getItem(int id){
        return itemRepository.getItem(id);
    }

    public ResponseEntity<String> addItemToFavoriteList(String username, int itemId) {
        Item item = itemRepository.getItem(itemId);
        if (item == null) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }

        itemRepository.addItemToFavoriteList(username, itemId);
        return new ResponseEntity<>("Item added to favorite list", HttpStatus.OK);
    }

    public List<Integer> getAllFavoriteItems(String username){
        return itemRepository.getAllFavoriteItems(username);
    }
}

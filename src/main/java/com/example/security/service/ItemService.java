package com.example.security.service;

import com.example.security.model.Item;
import com.example.security.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}

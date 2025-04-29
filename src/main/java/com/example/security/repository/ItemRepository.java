package com.example.security.repository;

import com.example.security.model.Item;
import com.example.security.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ITEM_TABLE = "items";
    private static final String FAVORITE_ITEMS_TABLE = "favorite_items";


    public List<Item> getAllItems(){
            String sql = "SELECT * FROM "+ ITEM_TABLE;
            List<Item> allItems = jdbcTemplate.query(sql,new ItemMapper());
            System.out.println("Retrieved items: " + allItems.size());
            return allItems;

    }


    public Item getItem(int id){
            String sql = "SELECT * FROM "+ ITEM_TABLE + " WHERE id = ?";
            Item item = jdbcTemplate.queryForObject(sql,new ItemMapper(),id);
            return item;
    }

    public void addItemToFavoriteList(String username, int itemId) {
        String sql = "INSERT INTO " + FAVORITE_ITEMS_TABLE + " (username, item_id) VALUES (?,?)";
        jdbcTemplate.update(sql, username, itemId);
    }

    public List<Integer> getAllFavoriteItems(String usernsme){
        String sql = "SELECT item_id FROM "+ FAVORITE_ITEMS_TABLE+" WHERE username = ?";
        return jdbcTemplate.queryForList(sql,Integer.class,usernsme);
    }




}

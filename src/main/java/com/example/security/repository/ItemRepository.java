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

    public List<Item> getAllItems(){
        try{
            String sql = "SELECT * FROM "+ ITEM_TABLE;
            List<Item> allItems = jdbcTemplate.query(sql,new ItemMapper());
            System.out.println("Retrieved items: " + allItems.size());
            return allItems;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Item getItem(int id){
        try{
            String sql = "SELECT * FROM "+ ITEM_TABLE + " WHERE id = ?";
            Item item = jdbcTemplate.queryForObject(sql,new ItemMapper(),id);
            return item;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}

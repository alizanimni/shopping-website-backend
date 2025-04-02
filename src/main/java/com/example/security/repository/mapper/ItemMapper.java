package com.example.security.repository.mapper;

import com.example.security.model.CustomUser;
import com.example.security.model.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("photo"),
                rs.getDouble("price"),
                rs.getInt("item_quantity"));
    }
}

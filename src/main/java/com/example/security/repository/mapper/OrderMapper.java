package com.example.security.repository.mapper;

import com.example.security.model.CustomUser;
import com.example.security.model.Order;
import com.example.security.model.OrderStatus;
import com.example.security.model.OrderedItem;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper implements ResultSetExtractor<List<Order>> {
    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException {
        Map<Integer, Order> orderMap = new HashMap<>();

        while (rs.next()) {
            int orderId = rs.getInt("id");

            Order order = orderMap.get(orderId);
            if (order == null) {
                LocalDateTime orderDate = rs.getTimestamp("order_date") != null
                        ? rs.getTimestamp("order_date").toLocalDateTime()
                        : null;

                order = new Order(
                        orderId,
                        rs.getString("username"),
                        orderDate,
                        rs.getString("shipping_address"),
                        OrderStatus.valueOf(rs.getString("status")),
                        rs.getDouble("total_price"),
                        new ArrayList<>()
                );
                orderMap.put(orderId, order);
            }

            int itemId = rs.getInt("item_id");
            if (!rs.wasNull()) {
                OrderedItem item = new OrderedItem(
                        itemId,
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                order.getItemsOnOrder().add(item);
            }
        }

        return new ArrayList<>(orderMap.values());
    }
}

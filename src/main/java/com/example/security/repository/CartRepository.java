package com.example.security.repository;

import com.example.security.model.CustomUser;
import com.example.security.model.Item;
import com.example.security.model.Order;
import com.example.security.repository.mapper.ItemMapper;
import com.example.security.repository.mapper.OrderMapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class CartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ORDER_TABLE = "orders";
    private static final String ORDERED_ITEMS_TABLE ="ordered_items";

    public Order getCart(String username) {
        try {
            Integer orderId = searchCartByUserId(username);
            if (orderId == null) {
                System.out.println("No open order");
                return null;
            }
            Order order = getOrderById(orderId);

            return order;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Order getOrderById(int orderId){

        String sql = "SELECT o.id, o.username, o.order_date, o.shipping_address, o.total_price, o.status, " +
                "oi.item_id, oi.quantity, oi.price " +
                "FROM orders o LEFT JOIN ordered_items oi ON o.id = oi.order_id WHERE o.id = ?";

        List<Order> orders = jdbcTemplate.query(sql, new OrderMapper(), orderId);
        return orders.isEmpty() ? null : orders.get(0);
    }

    public Integer searchCartByUserId(String username) {
        try {
            String sql = "SELECT id FROM " + ORDER_TABLE + " WHERE username = ? AND status = 'OPEN'";
            return jdbcTemplate.queryForObject(sql, Integer.class, username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String createNewCart(String username,String address){
        try{
           String sql = "INSERT INTO "+ ORDER_TABLE+" (username,shipping_address) VALUES (?,?)";
           jdbcTemplate.update(sql,username,address);
           return "Cart added successfully";
       }catch (Exception e){
           System.err.println("Error create cart: " + e.getMessage());
           throw new RuntimeException("Failed to create cart", e);
       }
   }

    public String addItemToCart(int itemId, int orderId,int quantity,double price){
        try{
            String sql = "INSERT INTO "+ ORDERED_ITEMS_TABLE+" (item_id,order_id,quantity,price) VALUES (?,?,?,?)";
            jdbcTemplate.update(sql,itemId,orderId,quantity,price);
            return "Item added to cart successfully";
        }catch (Exception e){
            System.err.println("Error adding item to cart: " + e.getMessage());
            throw new RuntimeException("Failed to add item to cart", e);
        }
    }

    public boolean isItemExistOnCart(int itemId, int orderId) {
        String sql = "SELECT COUNT(*) FROM " + ORDERED_ITEMS_TABLE + " WHERE item_id = ? AND order_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, itemId, orderId);
        return count != null && count > 0;
    }

    public void updateItemInCart(int itemId, int orderId, int quantity, double price) {
        String sql = "UPDATE " + ORDERED_ITEMS_TABLE + " SET quantity = quantity + ?, price = price + ? WHERE item_id = ? AND order_id = ?";
        jdbcTemplate.update(sql, quantity, price, itemId, orderId);
    }

    public void decreaseItemInCart(int itemId, int orderId, int quantity, double price) {
        String sql = "UPDATE " + ORDERED_ITEMS_TABLE +
                " SET quantity = quantity - ?, price = price - ? " +
                "WHERE item_id = ? AND order_id = ?";
        jdbcTemplate.update(sql, quantity, price, itemId, orderId);
    }



    public List<Order> getAllUserOrders(String username) {
        try {
            String sql = "SELECT id FROM " + ORDER_TABLE + " WHERE username = ?";
            List<Integer> orderIds = jdbcTemplate.queryForList(sql, Integer.class, username);

            List<Order> orders = new ArrayList<>();
            for (Integer id : orderIds) {
                Order order = getOrderById(id);
                if (order != null) {
                    orders.add(order);
                }
            }

            return orders;

        } catch (DataAccessException e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
            return Collections.emptyList();
        }
    }



    public String closeOrder(String username, int cartId){
        try{
           String sql = "UPDATE "+ ORDER_TABLE + " SET status = 'CLOSE' WHERE username = ? AND id = ?";
            jdbcTemplate.update(sql, username,cartId);
            return "Order closed";
        }catch (Exception e){
            return e.getMessage();
        }
    }



}

package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private String username;
    @JsonProperty(value = "order_date")
    private LocalDateTime orderDate;
    @JsonProperty(value = "shipping_address")
    private String shippingAddress;
    @JsonProperty(value = "total_price")
    private double totalPrice;
    @JsonProperty(value = "status")
    private OrderStatus orderStatus;
    private List<OrderedItem> itemsOnOrder;


    public Order(String username, LocalDateTime orderDate, String shippingAddress, double totalPrice, OrderStatus orderStatus,List<OrderedItem> itemsOnOrder) {
        this.username = username;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.itemsOnOrder=itemsOnOrder;
    }

    public Order(int id, String username, LocalDateTime orderDate, String shippingAddress, OrderStatus orderStatus, double totalPrice,List<OrderedItem> itemsOnOrder) {
        this.id = id;
        this.username = username;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.itemsOnOrder=itemsOnOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderedItem> getItemsOnOrder() {
        return itemsOnOrder;
    }

    public void setItemsOnOrder(List<OrderedItem> itemsOnOrder) {
        this.itemsOnOrder = itemsOnOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", username=" + username +
                ", orderId=" + orderDate +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                '}';
    }
}

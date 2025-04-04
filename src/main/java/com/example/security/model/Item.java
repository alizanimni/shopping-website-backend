package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private int id;
    private String name;
    private String photo;
    private double price;
    @JsonProperty(value = "item_quantity")
    private int itemQuantity;

    public Item(String name, String photo, double price, int itemQuantity) {
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.itemQuantity = itemQuantity;
    }

    public Item(int id,String name, String photo, double price, int itemQuantity) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.itemQuantity = itemQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", itemQuantity=" + itemQuantity +
                ", id=" + id +
                '}';
    }
}

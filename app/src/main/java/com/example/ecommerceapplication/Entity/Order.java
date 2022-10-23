package com.example.ecommerceapplication.Entity;

public class Order {
    private String Id;
    private String Name;
    private String Quantity;
    private String Price;

    public Order(){ }

    public Order(String id, String name, String quantity, String price) {
        Id = id;
        Name = name;
        Quantity = quantity;
        Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}

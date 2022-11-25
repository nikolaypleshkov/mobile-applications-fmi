package com.example.ecommerceapplication.data.model;

import java.util.List;

public class SubmitOrder {
    private String Name;
    private String Email;
    private String Address;
    private String Total;
    private String Status;

    private List<Order> items;

    public SubmitOrder() { }

    public SubmitOrder(String name, String email, String address, String total, List<Order> items) {
        Name = name;
        Email = email;
        Address = address;
        Total = total;
        Status = "0";
        this.items = items;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }
}

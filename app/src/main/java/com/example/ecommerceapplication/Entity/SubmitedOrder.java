package com.example.ecommerceapplication.Entity;

import java.util.List;

public class SubmitedOrder {
    private String Name;
    private String Phone;
    private String Address;
    private String Total;
    private String Status;
    private List<Order> orders;

    public SubmitedOrder() { }

    public SubmitedOrder(String name, String phone, String address, String total, List<Order> orders) {
        Name = name;
        Phone = phone;
        Address = address;
        Total = total;
        this.orders = orders;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

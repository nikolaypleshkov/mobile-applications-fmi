package com.example.ecommerceapplication.data.model;

import java.util.List;

public class SubmitOrder {
    private String Number;
    private String Email;
    private String Address;
    private String Total;
    private String Status;

    private List<Order> items;

    public SubmitOrder() { }

    public SubmitOrder(String number, String email, String status, String address, String total, List<Order> items) {
        Number = number;
        Email = email;
        Address = address;
        Total = total;
        Status = status;
        this.items = items;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
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

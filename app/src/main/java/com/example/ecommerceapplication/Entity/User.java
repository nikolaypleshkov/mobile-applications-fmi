package com.example.ecommerceapplication.Entity;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String Name;
    private String Email;
    private String Phone;
    private String Password;

    public User(){ }

    public User(
            UUID uuid,
            String Name,
            String Email,
            String Phone,
            String Password
    ){
        this.uuid = uuid;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Password = Password;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

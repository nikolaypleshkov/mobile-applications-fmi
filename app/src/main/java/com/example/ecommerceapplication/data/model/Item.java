package com.example.ecommerceapplication.data.model;

public class Item {
    String Name;
    String Image;
    String Description;
    String Price;
    String MenuID;

    public Item(){ }

    public Item(String name, String image, String description, String price, String menuID) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}

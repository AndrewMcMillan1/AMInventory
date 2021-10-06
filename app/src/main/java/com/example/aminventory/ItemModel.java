package com.example.aminventory;

public class ItemModel {

    //Object variables
    private int id;
    private String name;
    private int quantity;

    //Constructor
    public ItemModel(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public ItemModel() {
    }

    //Item model to string method for grid view
    @Override
    public String toString() {
        return "ITEM\n" +
                "ID: " + id +
                "\nNAME: " + name +
                "\nQUANTITY: " + quantity;
    }

    //Get and set methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

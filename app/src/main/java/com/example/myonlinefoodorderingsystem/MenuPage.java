package com.example.myonlinefoodorderingsystem;

public class MenuPage {
    private String name;
    private double price;

    public MenuPage(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

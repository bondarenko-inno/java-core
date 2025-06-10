package org.example;

public class OrderItem {
    public OrderItem(String productName, double price, int quantity, Category category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    private String productName;
    private int quantity;
    private double price;
    private Category category;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}



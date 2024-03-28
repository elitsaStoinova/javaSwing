package model;

import java.awt.*;

public class Product {
    private String name;
    private double price;
    private Image productImage;

    private int toBuyCount=0;


    public Product(){}


    public Product(String name, double price, Image productImage) {
        this.name = name;
        this.price = price;
        this.productImage = productImage;
    }


    @Override
    public String toString() {
        return this.name + " " + this.price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public int getToBuyCount() {
        return toBuyCount;
    }

    public void setToBuyCount(int toBuyCount) {
        this.toBuyCount = toBuyCount;
    }
}

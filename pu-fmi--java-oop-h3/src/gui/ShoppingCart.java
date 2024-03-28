package gui;

import model.Product;
import model.MainCategory;
import model.Subcategory;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products = new ArrayList<>();

    public void addProductToCart(Product product){
        products.add(product);
    }

    public double getFinalSum(){
        double sum=0;
        for(Product p: products)
            sum += p.getToBuyCount()*p.getPrice();
        return sum;
    }

    public int getElementsCount(){
        return products.size();
    }

    public List<Product> getProducts(){
        return products;
    }


    public int getAllProductsCount(){
        int sum=0;
        for(Product p: products)
            sum += p.getToBuyCount();
        return sum;
    }
}
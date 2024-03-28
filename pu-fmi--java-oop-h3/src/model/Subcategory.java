package model;

import comparators.PriceComparator;
import comparators.ProductComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Subcategory implements ICategory{
    private String name;
    private List<Product> products = new ArrayList<>();


    public void addProduct(Product product){
        products.add(product);
    }

    public Subcategory() {

    }

    public Subcategory(String name) {
        this.name = name;
    }

    @Override
    public String returnElements(){
        String result = "";
        for(Product p : products)
            result += p.toString() + " \n";
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

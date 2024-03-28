package model;

import java.util.ArrayList;
import java.util.List;

public class MainCategory implements ICategory {
    private String name;
    private List<Subcategory> subcategories = new ArrayList<>();


    public void addSubcategory(Subcategory subcategory){
        subcategories.add(subcategory);
    }

    @Override
    public String returnElements() {
        String result="";
        for(Subcategory s:subcategories)
            result += s.getName() + "\n";
        return result;
    }

    public MainCategory() {

    }
    public MainCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}

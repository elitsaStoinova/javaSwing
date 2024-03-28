package gui;
import comparators.PriceComparator;
import comparators.ProductComparator;
import model.Product;
import model.MainCategory;
import model.Subcategory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CategoriesData {

    private List<MainCategory> categories = new ArrayList<>();
    private MainCategory mCategory = new MainCategory();
    private Subcategory subcategory = new Subcategory();

    public void getInfo(){
        File myFile = new File("online_shop_database.txt");
        try {
            Scanner reader = new Scanner(myFile);
            String dataLine="";
            while (reader.hasNextLine()) {
                dataLine = reader.nextLine();
                if (dataLine.charAt(0) == '<') {
                    if (dataLine.charAt(1) == '/')
                        continue;
                    else {
                        dataLine = dataLine.replace("<", "");
                        dataLine = dataLine.replace(">", "");
                        mCategory = new MainCategory(dataLine);
                        categories.add(mCategory);
                    }
                } else if (dataLine.charAt(0) == '_') {
                    dataLine = dataLine.replace("_", "");
                    subcategory = new Subcategory(dataLine);
                    mCategory.addSubcategory(subcategory);
                } else if (dataLine.charAt(0) == '*') {
                    Product product = new Product();
                    dataLine = dataLine.replace("*", "");
                    String[] el = dataLine.split(",");
                    product.setName(el[0]);
                    product.setPrice(Double.parseDouble(el[1]));
                    String imagePath = el[2].toString();
                    Image image = ImageIO.read(new File(imagePath));
                    product.setProductImage(image);
                    subcategory.addProduct(product);
                }
                ProductComparator comparator = new PriceComparator();
                Collections.sort(subcategory.getProducts(),comparator);
            };
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MainCategory> getCategories() {
        return categories;
    }

}

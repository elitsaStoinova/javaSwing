package comparators;

import model.Product;

import javax.swing.*;
import java.util.Comparator;


public abstract class ProductComparator implements Comparator<Product> {
    protected int sortOrder = 1;

    public void setSortOrder(SortOrder sortOrder) {

        if (sortOrder.equals(SortOrder.DESCENDING)) {
            this.sortOrder = -1;
        } else {
            this.sortOrder = 1;
        }
    }

}

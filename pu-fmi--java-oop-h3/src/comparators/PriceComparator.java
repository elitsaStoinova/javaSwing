package comparators;

import  model.Product;
public class PriceComparator extends ProductComparator {

    @Override
    public int compare(Product product1, Product product2) {

        if (product1.getPrice() < product2.getPrice()) {
            return -1 * sortOrder;
        } else if (product1.getPrice()> product2.getPrice()) {
            return 1 * sortOrder;
        } else {
            return 0;
        }
    }

}

package comparators;
import model.Product;
public class NameComparator extends ProductComparator{
    @Override
    public int compare(Product product1, Product product2) {

        if (product1.getName().equals(product2.getName())) {
            return 0;
        } else {
            return (product1.getName().compareTo(product2.getName()) * sortOrder);
        }
    }

}

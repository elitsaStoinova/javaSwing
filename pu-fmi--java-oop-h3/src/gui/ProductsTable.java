package gui;
import model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductsTable extends AbstractTableModel {

    private List<Product> products = new ArrayList<>();

    public ProductsTable(List<Product> products){
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return this.products.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return products.get(rowIndex).getName();
            case 1:
                return products.get(rowIndex).getToBuyCount();
            case 2:
                return products.get(rowIndex).getPrice();
            case 3:
                return String.format("%,.2f",products.get(rowIndex).getPrice()*products.get(rowIndex).getToBuyCount());
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Име";
            case 1:
                return "Количество";
            case 2:
                return "Единична цена";
            case 3:
                return "Обща цена";
        }

        return super.getColumnName(column);
    }

}

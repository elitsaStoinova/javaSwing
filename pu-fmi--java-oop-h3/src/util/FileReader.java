package util;
import gui.ShoppingCart;
import model.Product;

import java.io.*;
import java.util.List;

public class FileReader {

    public FileReader(String s, boolean isAppendToFile) {
    }

    public static void createProductsFile() {
        File file = new File("src/files/products.file");
        file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Файлът не може да бъде създаден!");
            e.printStackTrace();
        }
    }

    public static void writeProducts(ShoppingCart cart) {
        boolean isAppendToFile = true;
        Writer writer = null;

        try {
            writer = new BufferedWriter(new FileWriter("src/files/products.file", isAppendToFile));
            writer.append("Фактура\n");
            for (Product p: cart.getProducts()) {
                writer.append(" Име: " + p.getName() + "\t");
                writer.append(" Брой: " +String.valueOf(p.getToBuyCount()) + "\t");
                writer.append("Единична цена: " + String.valueOf(p.getPrice())+ "\t");
                writer.append("Обща сума: " + String.valueOf(p.getPrice()*p.getToBuyCount())+ "\t");
                writer.append("\n");
            }
            writer.append("Финална сума: " + String.format("%,.2f",cart.getFinalSum()));
        } catch (IOException e) {
            System.err.println("Записът не може да бъде добавен във файла!");
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Записвачът не може да бъде затворен!");
                    e.printStackTrace();
                }
            }
        }
    }

}

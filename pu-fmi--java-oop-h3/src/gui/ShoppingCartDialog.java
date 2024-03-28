package gui;

import model.Product;
import util.FileReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ShoppingCartDialog extends JDialog {
    private FileReader fileReader;
    public ShoppingCartDialog(Frame parent, ShoppingCart cart){
        super(parent, "Вашата количка: ",true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel tableAndLabelPanel = new JPanel(new BorderLayout());

        ProductsTable productsTable = new ProductsTable(cart.getProducts());
        JTable table = new JTable(productsTable);

        JScrollPane scrollPane = new JScrollPane(table);
        tableAndLabelPanel.add(scrollPane, BorderLayout.CENTER);

        JLabel label = new JLabel("Финална сума: " + String.format("%,.2f",cart.getFinalSum()));
        label.setHorizontalAlignment(JLabel.CENTER);
        tableAndLabelPanel.add(label, BorderLayout.SOUTH);

        JPanel paymentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel payment = new JLabel("Изберете метод на плащане");
        paymentButtonPanel.add(payment);

        JButton payment1 = new JButton("Кредитна/дебитна карта.");
        JButton payment2 = new JButton("Paypal");
        payment1.setBackground(Color.white);
        payment2.setBackground(Color.white);
        paymentButtonPanel.add(payment1);
        paymentButtonPanel.add(payment2);
        payment1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFile(cart);
            }
        });


        payment2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFile(cart);
            }
        });

        panel.add(tableAndLabelPanel, BorderLayout.CENTER);
        panel.add(paymentButtonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
    }

    public void createFile(ShoppingCart cart){
        FileReader.createProductsFile();
        FileReader.writeProducts(cart);
    }
}

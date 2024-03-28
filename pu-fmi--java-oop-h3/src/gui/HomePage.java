package gui;
//import com.sun.glass.ui.Size;
import comparators.NameComparator;
import comparators.PriceComparator;
import comparators.ProductComparator;
import model.Product;
import model.MainCategory;
import model.Subcategory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class HomePage {

    private static CategoriesData data = new CategoriesData();
    private  DefaultMutableTreeNode subCategory;
    private JTree categoryTree;
    private JPanel panelRight;
    private JPanel panelLeft;
    private JPanel productsPanel;

    private JPanel centerPanel;
    private Subcategory currentSubcategory;

    private ShoppingCart cart = new ShoppingCart();


    private JFrame homeFrame;
    public static void main(String[] args) {
        HomePage gui = new HomePage();
        data.getInfo();
        gui.createHomePage();

    }

    public void createHomePage(){
        homeFrame = new JFrame("Online shop");
        homeFrame.setFont(new Font("Arial",Font.BOLD,30));
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setSize(900,800);
        homeFrame.setLocationRelativeTo(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        panelRight= new JPanel();
        panelRight.setBackground(Color.white);
        createRightPanel(panelRight,border);

        panelLeft= new JPanel();
        panelLeft.setBackground(Color.white);
        createLeftPanel(panelLeft,border);


        homeFrame.setVisible(true);
    }


    public void createRightPanel(JPanel panelRight, Border border){
        panelRight.setPreferredSize(new Dimension(700,800));
        panelRight.setBorder(border);
        panelRight.setLayout(new GridBagLayout());
        homeFrame.add(panelRight,BorderLayout.EAST);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel labelTitle = new JLabel("WELCOME!",SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial",Font.BOLD,22));
        panelRight.add(labelTitle,constraints);

        JLabel labelSubTitle = new JLabel("Please, select a category.",SwingConstants.CENTER);
        labelSubTitle.setFont(new Font("Arial",Font.BOLD,18));
        constraints.gridy = 1; // Move to the next row
        panelRight.add(labelSubTitle, constraints);

        panelRight.setVisible(true);
        homeFrame.add(panelRight);
        homeFrame.setVisible(true);
    }

    public void createLeftPanel(JPanel panelLeft, Border border){
        panelLeft.setPreferredSize(new Dimension(200,800));
        panelLeft.setBorder(border);
        homeFrame.add(panelLeft,BorderLayout.WEST);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
        for(MainCategory m : data.getCategories()) {
            DefaultMutableTreeNode mainCategoryNode = new DefaultMutableTreeNode(m.getName());
            for(Subcategory s : m.getSubcategories()) {
                subCategory = new DefaultMutableTreeNode(s.getName());
                mainCategoryNode.add(subCategory);
            }
            root.add(mainCategoryNode);
        }

        categoryTree = new JTree(root);
        categoryTree.setCellRenderer(new CustomTreeCellRenderer());
        JScrollPane treeScrollPane = new JScrollPane(categoryTree);

        categoryTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = categoryTree.getClosestRowForLocation(e.getX(), e.getY());
                categoryTree.setSelectionRow(row);


                TreePath path = categoryTree.getPathForRow(row);
                if (path != null) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

                    if (selectedNode != null && selectedNode.getLevel() == 2) {

                        for(MainCategory m : data.getCategories())
                            for(Subcategory s : m.getSubcategories())
                                if(s.getName().equals(selectedNode.getUserObject().toString())) {
                                    currentSubcategory = s;
                                }

                        productsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
                        productsPanel.setLayout(new BorderLayout());
                        productsPanel.setBackground(Color.white);
                        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                        topRightPanel.setBackground(Color.white);
                        createSortPanel(topRightPanel);
                        productsPanel.add(topRightPanel, BorderLayout.NORTH);
                        centerPanel = new JPanel(new GridLayout(0, 3, 10, 10));
                        centerPanel.setBackground(Color.white);
                        centerPanel.setPreferredSize(new Dimension(800, 800));

                        for(Product p : currentSubcategory.getProducts()) {
                            JPanel productPanel = new JPanel();
                            productPanel.setBackground(Color.white);
                            createOneProductPanel(productPanel, p);
                            centerPanel.add(productPanel);
                        }


                        JPanel cartPanel = new JPanel();
                        cartPanel.setBackground(Color.white);
                        createCartPanel(cartPanel);

                        productsPanel.add(cartPanel,BorderLayout.SOUTH);
                        centerPanel.setVisible(true);
                        productsPanel.add(centerPanel, BorderLayout.CENTER);
                        productsPanel.setVisible(true);
                        homeFrame.add(productsPanel);
                        productsPanel.revalidate();
                        productsPanel.repaint();
                        panelRight.setVisible(false);
                    }
                }
            }
        });

        panelLeft.add(treeScrollPane,BorderLayout.CENTER);
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setVisible(true);
    }

    public void createSortPanel(JPanel topRightPanel){
        JButton buttonSortName = new JButton("Сортирай по име");
        JButton buttonSortPrice = new JButton("Сортирай по цена");


        buttonSortName.setPreferredSize(new Dimension(180, 30));
        buttonSortPrice.setPreferredSize(new Dimension(180, 30));
        buttonSortName.setBackground(Color.white);
        buttonSortPrice.setBackground(Color.white);

        topRightPanel.add(buttonSortName);
        topRightPanel.add(buttonSortPrice);

        buttonSortPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductComparator comparator = new PriceComparator();
                List<Product> sorted = new ArrayList<>(currentSubcategory.getProducts());
                Collections.sort(sorted,comparator);
                refreshProductsDisplay(sorted);
            }
        });

        buttonSortName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductComparator comparator = new NameComparator();
                List<Product> sorted = new ArrayList<>(currentSubcategory.getProducts());
                Collections.sort(sorted,comparator);
                refreshProductsDisplay(sorted);
            }
        });

    }
    public void createCartPanel(JPanel cartPanel){
        ImageIcon imageIcon = new ImageIcon("images/cart.jpg");
        Image image = imageIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        JLabel text = new JLabel("Наличност в количката.");
        text.setVisible(false);

        imageLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ShoppingCartDialog dialog = new ShoppingCartDialog(homeFrame,cart);
                dialog.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                text.setText("Имате " + cart.getAllProductsCount() + " артикула във вашата количка.");
                text.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                text.setVisible(false);
            }
        });

        imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
//                text.setText("Имате " + cart.getAllProductsCount() + " артикула във вашата количка.");
//                text.setVisible(true);
            }
        });

        cartPanel.add(imageLabel);
        cartPanel.add(text);
    }
    public void createOneProductPanel(JPanel panel, Product p){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 350));

        JPanel horizontalCenterPanel = new JPanel(new GridBagLayout());
        horizontalCenterPanel.setBackground(Color.white);
        panel.add(horizontalCenterPanel);

        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.insets = new Insets(5, 5, 5, 5);
        centerConstraints.gridx = 0;
        centerConstraints.gridy = GridBagConstraints.RELATIVE;
        centerConstraints.fill = GridBagConstraints.HORIZONTAL;
        centerConstraints.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel =  new JLabel(p.getName(),SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalCenterPanel.add(titleLabel, centerConstraints);

        Image image = p.getProductImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);;
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBackground(Color.white);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalCenterPanel.add(imageLabel, centerConstraints);

        JLabel priceLabel = new JLabel(String.valueOf(p.getPrice()) + " лв.",SwingConstants.CENTER);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalCenterPanel.add(priceLabel, centerConstraints);

        ImageIcon cartIcon = new ImageIcon("images/cart.jpg");
        Image cartImage = cartIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(cartImage);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.white);
        createProductBottomPanel(bottomPanel,p);

        horizontalCenterPanel.add(bottomPanel, centerConstraints);
        panel.setVisible(true);
    }


    public void createProductBottomPanel(JPanel bottomPanel, Product p){
        ImageIcon cartIcon = new ImageIcon("images/cart.jpg");
        Image cartImage = cartIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(cartImage);

        JTextField textField = new JTextField(3);
        textField.setText("0");

        ImageIcon upIcon = new ImageIcon("images/upIcon.png");
        Image upIconImage = upIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        ImageIcon upImageIcon = new ImageIcon(upIconImage);
        ImageIcon downIcon = new ImageIcon("images/downIcon.png");
        Image downIconImage = downIcon.getImage().getScaledInstance(10,  10, Image.SCALE_SMOOTH);
        ImageIcon downImageIcon = new ImageIcon(downIconImage);

        JButton upButton = new JButton(upImageIcon);
        upButton.setBackground(Color.white);
        upButton.setPreferredSize(new Dimension(20, 20));

        JButton downButton = new JButton(downImageIcon);
        downButton.setBackground(Color.white);
        downButton.setPreferredSize(new Dimension(20, 20));

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt(textField.getText());
                textField.setText(String.valueOf(currentValue + 1));
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentValue = Integer.parseInt(textField.getText());
                currentValue -=1;
                if(currentValue<0){
                    currentValue = 0;
                }
                textField.setText(String.valueOf(currentValue));
            }
        });

        p.setToBuyCount(Integer.parseInt(textField.getText()));
        JButton cartButton = new JButton(icon);
        cartButton.setBackground(Color.white);
        cartButton.setPreferredSize(new Dimension(35, 30));
        cartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(textField.getText())==0){
                    textField.setText(String.valueOf(1));
                    p.setToBuyCount(1);
                }else p.setToBuyCount(Integer.parseInt(textField.getText()));
                cart.addProductToCart(p);
            }
        });

        bottomPanel.add(textField);
        bottomPanel.add(upButton);
        bottomPanel.add(downButton);
        bottomPanel.add(cartButton);
        bottomPanel.setVisible(true);
    }
    public void refreshProductsDisplay(List<Product> list) {
        centerPanel.removeAll();
        for (Product p : list) {
            JPanel productPanel = new JPanel();
            createOneProductPanel(productPanel, p);
            centerPanel.add(productPanel);
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

}

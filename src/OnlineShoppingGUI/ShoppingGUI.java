package OnlineShoppingGUI;
import OnlineShoppingSystem.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static OnlineShoppingSystem.WestminsterShoppingManager.pList;

public class ShoppingGUI extends JFrame {

    private final JComboBox<String> dropBox;
    private final DefaultTableModel tableModel;

    ShoppingCart shoppingCart = new ShoppingCart();

    public ShoppingGUI(){

        CartGUI cart = new CartGUI();

        setTitle("Westminster Shopping Centre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //create topPanel
        JPanel topPanel = new JPanel(new GridLayout(2,1));

        //First row in topPanel --> create shopping cart button
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cartButton = new JButton("Shopping Cart");
        cartPanel.add(cartButton);
        topPanel.add(cartPanel);

        //Second row in topPanel --> create dropbox
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel selectCategoryLabel = new JLabel("Select Product Category");
        dropBox = new JComboBox<>(new String[]{"All", "Clothing", "Electronics"});
        dropdownPanel.add(selectCategoryLabel);
        dropdownPanel.add(dropBox);
        topPanel.add(dropdownPanel);

        //Action listener for dropbox
        dropBox.addActionListener(e -> {     //lambda function for action listener
            String selected = (String) dropBox.getSelectedItem();  //type casting to string
            if (selected == null) throw new AssertionError();
            tableChoice(selected);
        });

        //Action listener for shopping cart button
        cartButton.addActionListener(e -> cart.setVisible(true));

        //Add topPanel to frame
        getContentPane().add(topPanel, BorderLayout.NORTH);

        //Create middle panel
        JPanel middlePanel = new JPanel(new BorderLayout());

        //Create table model
        String[] columnNames = {"Product ID", "Name", "Category","Price(£)", "Info"};
        tableModel = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;         // Make all cells non-editable
            }
        };

        //Populate table model
        populateTableModel(pList);

        //Create table
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 5*table.getRowHeight()));

        // Create a custom cell renderer to set colors based on less than 3 available items
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int availableItems;
                Object productId = table.getValueAt(row, 0); // Assuming the Product ID is in the first column
                if (productId != null) {
                    for (Product p:pList){
                        if (Objects.equals(productId, p.getProductID())){
                            availableItems = p.getNumAvailable();
                            if (availableItems < 3) {
                                cellComponent.setBackground(Color.RED); // Set the background color to red for cells with less than 3 items available
                            } else {
                                cellComponent.setBackground(table.getBackground()); // Set default background color
                            }
                        }
                    }
                }

                return cellComponent;
            }
        });

        //Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Labels for bottom panel to display selected object details
        JLabel selected = new JLabel("Selected Product - Details");
        JLabel Id = new JLabel();
        JLabel type = new JLabel();
        JLabel name = new JLabel();
        JLabel infoA = new JLabel();
        JLabel infoB = new JLabel();
        JLabel num = new JLabel();

        //add mouseEvent to table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String id = table.getValueAt(selectedRow, 0).toString();
                    Product selectedProduct = null;

                    for (Product p:pList){
                        if ( p.getProductID().equals(id)){
                            selectedProduct = p;
                            break;
                        }
                    }

                    if (selectedProduct != null){
                        Id.setText("Product Id: " + selectedProduct.getProductID());
                        name.setText("Name: " + selectedProduct.getProductName());
                        num.setText("Items Available: " + selectedProduct.getNumAvailable());

                        if (selectedProduct instanceof Clothing){
                            type.setText("Category: Clothing");
                            infoA.setText("Size: " + ((Clothing) selectedProduct).getDressSize());
                            infoB.setText("Colour: " + ((Clothing) selectedProduct).getDressColor());
                        }else if (selectedProduct instanceof Electronics){
                            type.setText("Category: Electronics");
                            infoA.setText("Brand: " + ((Electronics) selectedProduct).getBrand());
                            infoB.setText("Warranty Period (weeks): " + ((Electronics) selectedProduct).getWarrantyPeriod());
                        }
                    }else {
                        type.setText("Product not available");
                        infoA.setText("");
                        infoB.setText("");
                    }

                }
            }
        });

        //Add middlePanel to frame
        getContentPane().add(middlePanel, BorderLayout.CENTER);  // Add table to frame

        //create bottomPanel
        JPanel bottomPanel = new JPanel(new GridLayout(2,1));

        //create first row for jLabels
        JPanel detailsLabels = new JPanel(new GridLayout(7,1));

        //add labels to panel
        detailsLabels.add(selected);
        detailsLabels.add(Id);
        detailsLabels.add(type);
        detailsLabels.add(name);
        detailsLabels.add(infoA);
        detailsLabels.add(infoB);
        detailsLabels.add(num);
        bottomPanel.add(detailsLabels);

        //create second row for add to cart button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add to cart");
        buttonPanel.add(addButton);
        bottomPanel.add(buttonPanel);

        // Add button action listener to add to cart.
        addButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = table.getValueAt(selectedRow, 0).toString();
                Product selectedProduct = null;

                for (Product p : pList) {
                    if (p.getProductID().equals(id)) {
                        selectedProduct = p;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    if (selectedProduct.getNumAvailable() > 0) {
                        shoppingCart.addCart(selectedProduct);
                        CartGUI.populateCartTableModel(shoppingCart.getCartItems());
                        System.out.println("Product added to cart.");
                        updateTotalCostLabel();
                    } else {
                        System.out.println("Product not available.");
                    }
                }
            } else {
                System.out.println("Please select a product to add to the cart.");
            }
        });

        //add panel to frame
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Adjusting sizes and making the frame visible
        pack(); // Packs the components nicely within the frame
        setVisible(true); // Make the frame visible

    }

    //Populate table method according to the product type
    void populateTableModel(ArrayList<Product> list){
        // Clear existing data from the table model
        tableModel.setRowCount(0);

        // Add rows based on the chosen list
        for (Product p: list) {

            if (p instanceof Clothing){
                String info = ((Clothing) p).getDressSize() + "," + ((Clothing) p).getDressColor();
                Object[] clothingRow =  {p.getProductID(), p.getProductName(), "Clothing", p.getPrice(), info};
                tableModel.addRow(clothingRow);

            } else if (p instanceof Electronics) {
                String info =((Electronics) p).getBrand() + "," + ((Electronics) p).getWarrantyPeriod() + " weeks warranty";
                Object[] electronicsRow = {p.getProductID(), p.getProductName(), "Electronics", p.getPrice(), info};
                tableModel.addRow(electronicsRow);
            }

        }
    }


    //To check selection from dropbox and populate tables accordingly
    void tableChoice(String choice){
        if (choice.equals("All")) {
            populateTableModel(pList);
        } else{
            ArrayList<Product> selectedList = new ArrayList<>();
            for (Product p:pList) {
                if ((choice.equals("Clothing") && p instanceof Clothing) ||
                        (choice.equals("Electronics") && p instanceof Electronics)) {
                    selectedList.add(p);
                }
            }
            populateTableModel(selectedList);
        }
    }

    //update cost method will be called in mouse listener to update labels whenever product is added
    private void updateTotalCostLabel() {
        CartGUI.cost.setText("Total Cost                                                             :   £" + shoppingCart.calcTotal());
        CartGUI.discountLabel.setText("Three items in same category discount (20%) :   £" +shoppingCart.discount());
        CartGUI.finalCost.setText("Final Total                                                             :    £" + shoppingCart.finalTotal());
    }

}

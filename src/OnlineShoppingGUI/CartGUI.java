package OnlineShoppingGUI;

import OnlineShoppingSystem.Clothing;
import OnlineShoppingSystem.Electronics;
import OnlineShoppingSystem.Product;
import OnlineShoppingSystem.ShoppingCart;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;


public class CartGUI extends JFrame {

    public static JLabel cost;
    public static JLabel discountLabel;
    public static JLabel finalCost;
    static DefaultTableModel cartTableModel;

    ShoppingCart cart = new ShoppingCart();
    public CartGUI() {

        setTitle("Shopping cart");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //TOP PANEL FOR TABLE
        JPanel topPanel = new JPanel(new BorderLayout());

        //create table model
        String[] columnNames = {"Product","Quantity","Price"};

        //make sure table not editable
        cartTableModel = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //populate table model
        populateCartTableModel(cart.getCartItems());

        //create table
        JTable table = new JTable(cartTableModel);

        //Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //add top panel to frame
        getContentPane().add(topPanel,BorderLayout.CENTER);

        //BOTTOM PANEL FOR LABELS
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel costPanel = new JPanel(new GridLayout(3,1));

        //Labels are made static at class level to access and set text when add products being done in the shoppingGUI class

        //create total label
        cost = new JLabel();
        costPanel.add(cost);

        //create more than 3 purchase discount label
        discountLabel = new JLabel();
        costPanel.add(discountLabel);

        //create final total label
        finalCost = new JLabel();
        costPanel.add(finalCost);

        //add labels to panel
        bottomPanel.add(costPanel);

        //add panel to frame
        getContentPane().add(bottomPanel,BorderLayout.SOUTH);

    }

    //method to populate cart table
    static void populateCartTableModel(ArrayList<Product> list) {
        // Clear existing data from the table model
        cartTableModel.setRowCount(0);

        for (Product p : list) {
            String info = p.getProductID() + ", " + p.getProductName();
            String extra = null;

            if (p instanceof Clothing) {
                extra = ((Clothing) p).getDressSize() + ", " + ((Clothing) p).getDressColor();
            } else if (p instanceof Electronics) {
                extra = ((Electronics) p).getBrand() + ", " + ((Electronics) p).getWarrantyPeriod();
            }

            int quantity;
            double price;

            // Check if the product is already in the table
            boolean found = false;

            for (int row = 0; row < cartTableModel.getRowCount(); row++) {
                //cart table contain objects, so convert to string after getting value from index and split from ","
                if (p.getProductID().equals(cartTableModel.getValueAt(row, 0).toString().split(", ")[0])) {
                    // if product found in the table, update quantity and price
                    quantity = (int) cartTableModel.getValueAt(row, 1) + 1;
                    price = p.getPrice() * quantity;
                    cartTableModel.setValueAt(quantity, row, 1);
                    cartTableModel.setValueAt(price, row, 2);
                    found = true;
                    break;
                }
            }

            if (!found) {
                // if product not found in the table, add a new row
                quantity = 1;
                price = p.getPrice() * quantity;
                Object[] tableRow = {(info + ", " + extra), quantity, price};
                cartTableModel.addRow(tableRow);
            }
        }
    }
}

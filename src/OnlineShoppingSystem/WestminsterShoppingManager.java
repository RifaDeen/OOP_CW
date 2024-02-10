package OnlineShoppingSystem;
import OnlineShoppingGUI.ShoppingGUI;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    public static ArrayList<Product> pList = new ArrayList<>();   //arraylist made public and static to access from anywhere in the program
    Scanner sc = new Scanner(System.in);

    @Override
    public void addProducts(){
        try {

            //to check if products are exceeding
            if (pList.size() >= 50) {
                System.out.println("Maximum number of products reached");
                return;
            }

            System.out.println();
            System.out.println("what type of product do you want to add?" + "\nPress 1 for clothing or 2 for electronics");
            int category = sc.nextInt();

            if (category == 1 || category == 2) {

                System.out.println("Enter product ID");
                String pId = sc.next();

                if (existing(pId)) {
                    System.out.println("Product already exists");
                } else {
                    System.out.println("Enter product name:");
                    String pName = sc.next();

                    System.out.println("Enter the number of items available");
                    int numAvailable = sc.nextInt();

                    System.out.println("Enter the price");
                    double price = sc.nextDouble();

                    if (category == 1) {

                        System.out.println("Enter the dress colour");
                        String colour = sc.next();
                        System.out.println("Enter the dress size (S-small,M-medium,L-large)");
                        String size = sc.next();

                        Product newCProduct = new Clothing(pId, pName, numAvailable, price, size, colour);
                        pList.add(newCProduct);

                    } else {

                        System.out.println("Enter the electronics brand");
                        String brand = sc.next();

                        System.out.println("Enter the warranty period in weeks");
                        int warranty = sc.nextInt();

                        Product newEProduct = new Electronics(pId, pName, numAvailable, price, brand, warranty);
                        pList.add(newEProduct);

                    }
                }
            } else {
                System.out.println("Invalid entry, pls re-enter");
                addProducts();
            }
        } catch (Exception e){
            System.out.println("Error occurred - Please enter valid data");
        }
    }

    //Method to check for existing products in arraylist
    public boolean existing(String id){
        for(Product i : pList){
            if( i.getProductID().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteProducts() {

        try {
            System.out.println("Enter the product ID to delete");
            String delId = sc.next();
            //use iterator to iterate through the product list and check is product exist
            Iterator<Product> iterator = pList.iterator();

            boolean found = false;
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (product.getProductID().equals(delId)) {
                    iterator.remove(); // Remove the element using iterator remove() method
                    System.out.println("The following product has been deleted;\n");
                    System.out.println(product.showProductInfo());
                    System.out.println("Total number of products left: " + pList.size());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Product doesn't exist");
            }

        } catch (Exception e) {
            System.out.println("An error occurred during product deletion.");
        }
    }

    @Override
    public void printProducts() {

        if (pList.isEmpty()) {
            System.out.println("No products available.");
        } else {
            // Sort the list using natural ordering defined by compareTo
            Collections.sort(pList);

            System.out.println("List of Products:\n");
            for (Product i : pList) {
                System.out.println(i.showProductInfo());
                System.out.println(" ");
            }
        }
        System.out.println("No of products= "+ pList.size());
    }

    @Override
    public void saveFile() {
        try {
            Collections.sort(pList);      //Sort the list using natural ordering defined by compareTo

            //Create buffered writer and open file
            BufferedWriter writer = new BufferedWriter(new FileWriter("Products"));

            for(Product i : pList) {
                writer.write(i.fileDetails());    //write details of the products from fileDetails() in products class
                writer.newLine();
            }
            writer.close();
            System.out.println("Product details stored in file successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    @Override
    public void loadFile(){
        try {



            //create buffered reader to read from file line by line
            BufferedReader reader = new BufferedReader(new FileReader("Products"));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length >= 7) {
                    String type = data[0];
                    String pId = data[1];
                    String pName = data[2];
                    int numAvailable = Integer.parseInt(data[3]);
                    double price = Double.parseDouble(data[4]);

                    if ("Clothing".equals(type)) {
                        String size = data[5];
                        String color = data[6];
                        Product clothing = new Clothing(pId, pName, numAvailable, price, size, color);
                        pList.add(clothing);
                    }
                    if ("Electronics".equals(type)) {
                        String brand = data[5];
                        int warranty = Integer.parseInt(data[6]);
                        Product electronics = new Electronics(pId, pName, numAvailable, price, brand, warranty);
                        pList.add(electronics);
                    }

                }
                //System.out.println(Arrays.toString(data));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void menu(){
        while (true) {
            System.out.println("""
                        ----------------------------------------------------------------------
                        Please select an option:\s
                        1) Add a new product\s
                        2) Delete a product\s
                        3) Print the list of the products\s
                        4) Save in a file\s
                        5) Open GUI\s
                              0) Quit\s
                        ----------------------------------------------------------------------""");
            try {

                System.out.println("Enter Option: ");
                int choose = sc.nextInt();

                switch (choose) {
                    case 0:   //0)terminates
                        System.out.println("Program exit, have a nice day!");
                        break;
                    case 1:   //1) add product
                        addProducts();
                        continue;
                    case 2:   //2) delete product
                        deleteProducts();
                        continue;
                    case 3:   //3)print products list
                        printProducts();
                        continue;
                    case 4:   //4)save in file
                        saveFile();
                        continue;
                    case 5:
                        //Create instance of shoppingGUI
                        ShoppingGUI GUI = new ShoppingGUI();
                        GUI.setVisible(true);
                        break;
                    default:
                        System.out.println("Invalid Option.");
                        continue;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("An error occurred");
                sc.nextLine();
            }
        }
    }

    //Entry point to the system both manager and user
    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.loadFile();    //loads the previously stored products
        manager.menu();
    }

}
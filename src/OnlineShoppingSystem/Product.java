package OnlineShoppingSystem;

import java.io.*;

public abstract class Product implements Comparable<Product>, Serializable {
    private String productID;
    private String productName;
    private int numAvailable;
    private double price;

    //constructor
    public Product(String productID, String productName, int numAvailable, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numAvailable = numAvailable;
        this.price = price;
    }

    public int compareTo(Product otherProduct) {
        return this.productID.compareTo(otherProduct.productID);
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumAvailable() {
        return numAvailable;
    }

    public void setNumAvailable(int numAvailable) {
        this.numAvailable = numAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //abstract method
    public abstract String showProductInfo();

    @Override
    public String toString() {
        return  "ProductID= " + getProductID() +
                " \nProductName= " + getProductName() +
                " \nNumber of items available= " + getNumAvailable() +
                " \nPrice= " + getPrice();
    }

    public String fileDetails(){
        return getProductID() + "," + getProductName() + ","
                + getNumAvailable() + "," + getPrice();
    }

}


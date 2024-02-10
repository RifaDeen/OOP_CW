package OnlineShoppingSystem;

public class Clothing extends Product {
    private String dressSize;
    private String dressColor;

    public Clothing(String productID, String productName, int numAvailable, double price, String dressSize, String dressColor) {
        super(productID, productName, numAvailable, price);
        this.dressSize = dressSize;
        this.dressColor = dressColor;
    }

    public String getDressSize() {
        return dressSize;
    }

    public void setDressSize(String dressSize) {
        this.dressSize = dressSize;
    }

    public String getDressColor() {
        return dressColor;
    }

    public void setDressColor(String dressColor) {
        this.dressColor = dressColor;
    }

    @Override
    public String showProductInfo() {
        return "Product type = Clothing\n" +
                super.toString()+
                "\nColour= " + getDressColor()+
                "\nSize= " + getDressSize();
    }

    @Override
    public String fileDetails(){
        return "Clothing" + "," + super.fileDetails() + "," +
                getDressColor() + "," + getDressSize();
    }
}

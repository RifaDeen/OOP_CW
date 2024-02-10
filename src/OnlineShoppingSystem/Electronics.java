package OnlineShoppingSystem;

public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, int numAvailable, double price, String brand, int warrantyPeriod) {
        super(productID, productName, numAvailable, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String showProductInfo() {
        return "Product type = Electronics\n"+
                super.toString()+
                "\nBrand= " + getBrand()+
                "\nWarranty Period= " + getWarrantyPeriod();
    }

    @Override
    public String fileDetails(){
        return "Electronics" + "," + super.fileDetails() + "," +
                getBrand() + "," + getWarrantyPeriod() ;
    }

}
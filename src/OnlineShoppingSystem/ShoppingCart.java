package OnlineShoppingSystem;
import java.util.*;

public class ShoppingCart {
    private ArrayList<Product> cartItems;

    //constructor
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Product> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCart(Product product){
        cartItems.add(product);
        setCartItems(cartItems);
    }

    public void removeCart(Product product){
        cartItems.remove(product);
    }

    public String calcTotal(){
        double cost = 0;
        for (Product i:cartItems) {
            cost = cost + i.getPrice();
        }
        return String.valueOf(cost);
    }

    Double discountPrice;

    public Double discount(){

        double totalCost = Double.parseDouble(calcTotal());
        int cCount = 0;
        int eCount = 0;

        for(Product p : cartItems){
            if (p instanceof Clothing){
                cCount++;
            }
            if (p instanceof Electronics){
                eCount++;
            }
        }

        if ((cCount > 2 || eCount > 2 ) ){
            discountPrice = totalCost*0.2;
        }

        return discountPrice != null ? discountPrice : 0.0;    //ternary conditional operator (condition? if true: if false)
    }

    public double finalTotal(){
        double totalCost = Double.parseDouble(calcTotal());
        return totalCost - discount();
    }

}

package OnlineShoppingSystem;

import junit.framework.TestCase;

//ref(https://www.tutorialspoint.com/junit/junit_writing_tests.htm)

public class TestProduct extends TestCase {


    public void testClothingProducts(){
        Clothing product = new Clothing("C001", "T-Shirt", 10, 19.99, "M", "Blue");

        assertEquals("C001", product.getProductID());
        assertEquals("T-Shirt", product.getProductName());
        assertEquals(10, product.getNumAvailable());
        assertEquals(19.99, product.getPrice());
        assertEquals("M", product.getDressSize());
        assertEquals("Blue", product.getDressColor());
    }

    public void testElectronicsProducts(){
        Electronics product = new Electronics("E001", "Laptop", 10, 20.0, "Asus", 48);

        assertEquals("E001", product.getProductID());
        assertEquals("Laptop", product.getProductName());
        assertEquals(10, product.getNumAvailable());
        assertEquals(20.0, product.getPrice());
        assertEquals("Asus", product.getBrand());
        assertEquals(48, product.getWarrantyPeriod());
    }

    public void testAddToCart() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Electronics("E002", "Headphones", 5, 79.99, "Sony", 12);

        cart.addCart(product);

        assertEquals(1, cart.getCartItems().size());
        assertTrue(cart.getCartItems().contains(product));
    }

    public void testCalcTotal() {
        ShoppingCart cart = new ShoppingCart();
        Product product1 = new Clothing("C004", "Shirt", 3, 29.99, "M", "White");
        Product product2 = new Electronics("E003", "Tablet", 2, 199.99, "Apple", 36);

        cart.addCart(product1);
        cart.addCart(product2);

        assertEquals("229.98000000000002", cart.calcTotal());
    }



}




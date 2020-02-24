package delivery;


import cart.Category;
import cart.Product;
import cart.ShoppingCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeliveryCostCalculatorTest {

    private ShoppingCart shoppingCart;

    private Product apple;
    private Product banana;
    private Product macBook;

    @Before
    public void init() {

        Category technologyCategory = new Category
                .Builder()
                .title("Technology")
                .build();

        Category laptopCategory = new Category
                .Builder()
                .title("Laptop")
                .parentCategory(technologyCategory)
                .build();

        Category foodCategory = new Category
                .Builder()
                .title("Food")
                .build();

        Category fruitCategory = new Category
                .Builder()
                .parentCategory(foodCategory)
                .title("Fruit")
                .build();

        apple = new Product("Apple", 10.0, fruitCategory);
        banana = new Product("Banana", 15.0, fruitCategory);
        macBook = new Product("MacBook", 12_500.0, laptopCategory);

        shoppingCart = new ShoppingCart();

    }

    @Test
    public void calculateFor_successfully() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        shoppingCart.addItem(apple, 1);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 4.99));

    }

    @Test
    public void calculateFor_successfullyWithMoreThanOneQuantityOfProduct() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        shoppingCart.addItem(apple, 10);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 4.99));

    }

    @Test
    public void calculateFor_successfullyWithDifferentProductsButSameCategory() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        shoppingCart.addItem(apple, 10);
        shoppingCart.addItem(banana, 4);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 5.99));

    }

    @Test
    public void calculateFor_successfullyWithDifferentProductsAndDifferentCategories() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        shoppingCart.addItem(apple, 10);
        shoppingCart.addItem(banana, 4);
        shoppingCart.addItem(macBook, 3);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 7.99));

    }

    @Test
    public void calculateFor_successfully_withDifferentCostPerDeliveryAndCostPerProduct() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2, 3);
        shoppingCart.addItem(apple, 1);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 7.99));

    }

    @Test
    public void calculateFor_successfullyWithMoreThanOneQuantityOfProduct_withDifferentCostPerDeliveryAndCostPerProduct() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2, 3);
        shoppingCart.addItem(apple, 10);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 7.99));

    }

    @Test
    public void calculateFor_successfullyWithDifferentProductsButSameCategory_withDifferentCostPerDeliveryAndCostPerProduct() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2, 3);
        shoppingCart.addItem(apple, 10);
        shoppingCart.addItem(banana, 4);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 10.99));

    }

    @Test
    public void calculateFor_successfullyWithDifferentProductsAndDifferentCategories_withDifferentCostPerDeliveryAndCostPerProduct() {

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2, 3);
        shoppingCart.addItem(apple, 10);
        shoppingCart.addItem(banana, 4);
        shoppingCart.addItem(macBook, 3);
        Assert.assertEquals(0, Double.compare(deliveryCostCalculator.calculateFor(shoppingCart), 15.99));

    }

}

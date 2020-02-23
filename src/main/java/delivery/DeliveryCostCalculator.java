package delivery;

import cart.Product;
import cart.ShoppingCart;

import java.util.stream.Collectors;

public class DeliveryCostCalculator implements IDeliveryCostCalculator {
    private double costPerDelivery;
    private double costPerProduct;
    private static final double FIXED_COST = 2.99;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    public double getCostPerDelivery() {
        return costPerDelivery;
    }

    public void setCostPerDelivery(double costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
    }

    public double getCostPerProduct() {
        return costPerProduct;
    }

    public void setCostPerProduct(double costPerProduct) {
        this.costPerProduct = costPerProduct;
    }

    /**
     * use to calculate delivery cost for this cart
     *
     * @param cart shopping cart
     */
    @Override
    public double calculateFor(ShoppingCart cart) {
        return (costPerDelivery * getNumberOfDeliveries(cart))
                + (costPerProduct * getNumberOfDifferentProduct(cart))
                + FIXED_COST;
    }

    private int getNumberOfDeliveries(ShoppingCart cart) {
        return cart.getShoppingCartItems()
                .keySet()
                .stream()
                .map(Product::getCategory)
                .collect(Collectors.toSet())
                .size();
    }

    private int getNumberOfDifferentProduct(ShoppingCart cart) {
        return cart.getShoppingCartItems()
                .keySet()
                .size();
    }
}

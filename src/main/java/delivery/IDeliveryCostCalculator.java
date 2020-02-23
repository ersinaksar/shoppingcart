package delivery;

import cart.ShoppingCart;

public interface IDeliveryCostCalculator {

    double calculateFor(ShoppingCart cart);

}

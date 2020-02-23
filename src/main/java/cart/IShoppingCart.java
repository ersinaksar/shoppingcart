package cart;

import discount.campaign.ICampaign;
import discount.coupon.ICoupon;

public interface IShoppingCart {

    void applyDiscounts(ICampaign... campaigns);

    void applyCoupon(ICoupon coupon);

    void print();

    double getDeliveryCost();

    void addItem(Product product, int quantity);

}

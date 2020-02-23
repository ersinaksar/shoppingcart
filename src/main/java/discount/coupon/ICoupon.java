package discount.coupon;

import discount.IDiscount;

/**
 * To facilitate code readability
 */
public interface ICoupon extends IDiscount {

    boolean isApplicable(double minPurchaseAmount, double purchaseAmount);

}

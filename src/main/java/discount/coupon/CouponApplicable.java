package discount.coupon;

public abstract class CouponApplicable {

    public boolean isApplicable(double minPurchaseAmount, double purchaseAmount) {
        return purchaseAmount > minPurchaseAmount;
    }

}

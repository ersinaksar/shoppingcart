package discount.coupon;

public class RateCoupon extends CouponApplicable implements ICoupon {

    private int minPurchaseAmount;
    private double rate;

    public RateCoupon(int minPurchaseAmount, double rate) {
        this.minPurchaseAmount = minPurchaseAmount;
        this.rate = rate;
    }

    public int getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(int minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * use to get discount for this category
     *
     * @param totalPrice total price of cart
     * @return double discount
     */
    @Override
    public double getDiscount(double totalPrice) {
        return rate * totalPrice / 100;
    }

}

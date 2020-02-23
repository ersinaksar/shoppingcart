package discount.coupon;

public class AmountCoupon extends CouponApplicable implements ICoupon {

    private int minPurchaseAmount;
    private double amount;

    public AmountCoupon(int minPurchaseAmount, double amount) {
        this.minPurchaseAmount = minPurchaseAmount;
        this.amount = amount;
    }

    public int getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(int minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public double getDiscount(double totalPrice) {
        return amount;
    }
}

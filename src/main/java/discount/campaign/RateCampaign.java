package discount.campaign;

import cart.Category;

public class RateCampaign extends CampaignApplicable implements ICampaign {

    private Category category;
    private double rate;
    private int minQuantityOfProduct;

    public RateCampaign(Category category, double rate, int minQuantityOfProduct) {
        this.category = category;
        this.rate = rate;
        this.minQuantityOfProduct = minQuantityOfProduct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getMinQuantityOfProduct() {
        return minQuantityOfProduct;
    }

    public void setMinQuantityOfProduct(int minQuantityOfProduct) {
        this.minQuantityOfProduct = minQuantityOfProduct;
    }

    /**
     * use to get discount for this category
     *
     * @param totalPriceOfThisCategory total price of this category
     * @return double discount
     */
    @Override
    public double getDiscount(double totalPriceOfThisCategory) {
        return rate * totalPriceOfThisCategory / 100;
    }

    @Override
    public String toString() {
        return "RateCampaign{" +
                "category=" + category.getTitle() +
                ", rate=" + rate +
                ", minQuantityOfProduct=" + minQuantityOfProduct +
                '}';
    }
}

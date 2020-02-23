package discount.campaign;

import cart.Category;

public class AmountCampaign extends CampaignApplicable implements ICampaign {

    private Category category;
    private double amount;
    private int minQuantityOfProduct;

    public AmountCampaign(Category category, double amount, int minQuantityOfProduct) {
        this.category = category;
        this.amount = amount;
        this.minQuantityOfProduct = minQuantityOfProduct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMinQuantityOfProduct() {
        return minQuantityOfProduct;
    }

    public void setMinQuantityOfProduct(int minQuantityOfProduct) {
        this.minQuantityOfProduct = minQuantityOfProduct;
    }

    @Override
    public double getDiscount(double totalPriceOfThisCategory) {
        return amount;
    }

    @Override
    public String toString() {
        return "AmountCampaign{" +
                "category=" + category.getTitle() +
                ", amount=" + amount +
                ", minQuantityOfProduct=" + minQuantityOfProduct +
                '}';
    }
}

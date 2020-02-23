package discount.campaign;

public abstract class CampaignApplicable {

    public boolean isApplicable(Long quantityOfProduct, int minQuantityOfProduct) {
        return quantityOfProduct > minQuantityOfProduct;
    }

}

package discount.campaign;

import discount.IDiscount;

/**
 * To facilitate code readability
 */
public interface ICampaign extends IDiscount {

    boolean isApplicable(Long quantityOfProduct, int minQuantityOfProduct);

}
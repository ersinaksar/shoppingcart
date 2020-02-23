package cart;

import discount.campaign.AmountCampaign;
import discount.campaign.ICampaign;
import discount.campaign.RateCampaign;
import discount.coupon.AmountCoupon;
import discount.coupon.ICoupon;
import discount.coupon.RateCoupon;
import exception.InvalidPriceException;
import exception.InvalidQuantityException;
import exception.NullDataException;
import org.junit.Assert;
import org.junit.Test;


public class ShoppingCartTest {

    @Test
    public void addItem_successfully() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 1);
        Assert.assertEquals(1, shoppingCart.getTotalQuantityOfProduct());

    }

    @Test
    public void addItem_successfully_addMultipleProducts() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, technologyCategory);
        Product phone = new Product("iphone", 7_500.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 1);
        shoppingCart.addItem(phone, 3);

        Assert.assertEquals(4, shoppingCart.getTotalQuantityOfProduct());

    }

    @Test
    public void addItem_successfully_addSameProductMultipleTimes() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 1);
        shoppingCart.addItem(laptop, 2);
        Assert.assertEquals(3, shoppingCart.getTotalQuantityOfProduct());

    }

    @Test
    public void addItem_successfully_addMultipleProductsWithDifferentCategories() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Category foodCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, technologyCategory);
        Product fish = new Product("tuna", 10.0, foodCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 1);
        shoppingCart.addItem(fish, 9);
        Assert.assertEquals(10, shoppingCart.getTotalQuantityOfProduct());

    }

    @Test
    public void addItem_successfully_addMultipleProductsWithDifferentCategoriesAndParentCategory() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Category computerCategory = new Category
                .Builder()
                .title("computer")
                .parentCategory(technologyCategory)
                .build();

        Category foodCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, computerCategory);
        Product fish = new Product("tuna", 10.0, foodCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 1);
        shoppingCart.addItem(fish, 9);
        Assert.assertEquals(10, shoppingCart.getTotalQuantityOfProduct());

    }

    @Test(expected = NullDataException.class)
    public void addItem_productIsNull_expectedNullDataException() {

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(null, 1);

    }

    @Test(expected = InvalidQuantityException.class)
    public void addItem_quantityOfProductIsInvalid_expectedInvalidQuantityException() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 0);

    }

    @Test(expected = NullDataException.class)
    public void addItem_categoryOfProductIsNull_expectedNullDataException() {

        Product laptop = new Product("macbook", 12_500.0, null);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

    }

    @Test(expected = InvalidPriceException.class)
    public void addItem_productPriceIsInvalid_expectedInvalidPriceException() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 0.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

    }

    @Test(expected = NullDataException.class)
    public void addItem_titleOfProductIsNull_expectedNullDataException() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product(null, 12_500.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

    }

    @Test
    public void applyDiscounts_successfullyRateCampaign() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICampaign campaign = new RateCampaign(technologyCategory, 10.0, 1);
        shoppingCart.applyDiscounts(campaign);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCampaignDiscount(), 1_000));

    }

    @Test
    public void applyDiscounts_successfullyAmountCampaign() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICampaign campaign = new AmountCampaign(technologyCategory, 1_000.0, 1);
        shoppingCart.applyDiscounts(campaign);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCampaignDiscount(), 1_000));

    }

    @Test
    public void applyDiscounts_successfullyRateCampaignWithAmountCampaign() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICampaign rateCampaign = new RateCampaign(technologyCategory, 10.0, 1);
        ICampaign amountCampaign = new AmountCampaign(technologyCategory, 500.0, 1);
        shoppingCart.applyDiscounts(rateCampaign, amountCampaign);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCampaignDiscount(), 1_000));

    }

    @Test
    public void applyDiscounts_successfullyAmountCampaignWithRateCampaign() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICampaign rateCampaign = new RateCampaign(technologyCategory, 1.0, 1);
        ICampaign amountCampaign = new AmountCampaign(technologyCategory, 1_000.0, 1);
        shoppingCart.applyDiscounts(rateCampaign, amountCampaign);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCampaignDiscount(), 1_000));

    }

    @Test
    public void applyDiscounts_notApplicableRateCampaign() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICampaign campaign = new RateCampaign(technologyCategory, 10.0, 100);
        shoppingCart.applyDiscounts(campaign);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCampaignDiscount(), 0));

    }

    @Test
    public void applyDiscounts_notApplicableAmountCampaign() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICampaign campaign = new AmountCampaign(technologyCategory, 1_000.0, 10);
        shoppingCart.applyDiscounts(campaign);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCampaignDiscount(), 0));

    }

    @Test(expected = NullDataException.class)
    public void applyDiscounts_campaignIsNull_expectedNullDataException() {

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.applyDiscounts(null);

    }

    @Test
    public void applyCoupon_successfullyRateCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon coupon = new RateCoupon(1, 10);
        shoppingCart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 1_000));

    }

    @Test
    public void applyCoupon_successfullyAmountCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon coupon = new AmountCoupon(1, 1_000);
        shoppingCart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 1_000));

    }

    @Test
    public void applyCoupon_successfullyRateCouponAndAmountCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon rateCoupon = new RateCoupon(1, 10);
        ICoupon amountCoupon = new AmountCoupon(1, 100);

        shoppingCart.applyCoupon(rateCoupon);
        shoppingCart.applyCoupon(amountCoupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 1_100));

    }

    @Test
    public void applyCoupon_notApplicableRateCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon coupon = new RateCoupon(100_000, 10);
        shoppingCart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 0));

    }

    @Test
    public void applyCoupon_notApplicableAmountCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon coupon = new AmountCoupon(100_000, 1_000);
        shoppingCart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 0));

    }

    @Test
    public void applyCoupon_successfullyRateCouponAndNotApplicableAmountCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon rateCoupon = new RateCoupon(1, 10);
        ICoupon amountCoupon = new AmountCoupon(100_000, 100);

        shoppingCart.applyCoupon(rateCoupon);
        shoppingCart.applyCoupon(amountCoupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 1_000));

    }

    @Test
    public void applyCoupon_successfullyAmountCouponAndNotApplicableRateCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon rateCoupon = new RateCoupon(100_000, 10);
        ICoupon amountCoupon = new AmountCoupon(1, 100);

        shoppingCart.applyCoupon(rateCoupon);
        shoppingCart.applyCoupon(amountCoupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 100));

    }

    @Test
    public void applyCoupon_notApplicableAmountCouponAndNotApplicableRateCoupon() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 5_000.0, technologyCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 2);

        ICoupon rateCoupon = new RateCoupon(100_000, 10);
        ICoupon amountCoupon = new AmountCoupon(100_000, 100);

        shoppingCart.applyCoupon(rateCoupon);
        shoppingCart.applyCoupon(amountCoupon);
        Assert.assertEquals(0, Double.compare(shoppingCart.getCouponDiscount(), 0));

    }

    @Test
    public void getDeliveryCost_successfully() {

        Category technologyCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Category foodCategory = new Category
                .Builder()
                .title("technology")
                .build();

        Product laptop = new Product("macbook", 12_500.0, technologyCategory);
        Product fish = new Product("tuna", 10.0, foodCategory);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(laptop, 1);
        shoppingCart.addItem(fish, 9);

        Assert.assertEquals(0, Double.compare(shoppingCart.getDeliveryCost(), 6.99));

    }

    @Test
    public void getDeliveryCost_successfully_withEmptyCart() {

        ShoppingCart shoppingCart = new ShoppingCart();
        Assert.assertEquals(0, Double.compare(shoppingCart.getDeliveryCost(), 0));

    }

}

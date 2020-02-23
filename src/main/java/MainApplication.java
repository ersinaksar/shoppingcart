import cart.Category;
import cart.Product;
import cart.ShoppingCart;
import discount.campaign.AmountCampaign;
import discount.campaign.ICampaign;
import discount.campaign.RateCampaign;
import discount.coupon.AmountCoupon;
import discount.coupon.ICoupon;
import discount.coupon.RateCoupon;

public class MainApplication {

    public static void main(String[] args) {

        Category technologyCategory = new Category
                .Builder()
                .title("Technology")
                .build();

        Category laptopCategory = new Category
                .Builder()
                .title("Laptop")
                .parentCategory(technologyCategory)
                .build();

        Category foodCategory = new Category
                .Builder()
                .title("Food")
                .build();

        Category fruitCategory = new Category
                .Builder()
                .parentCategory(foodCategory)
                .title("Fruit")
                .build();

        Product apple = new Product("Apple", 10.0, fruitCategory);
        Product banana = new Product("Banana", 15.0, fruitCategory);
        Product macBook = new Product("MacBook", 12_500.0, laptopCategory);


        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 15);
        shoppingCart.addItem(banana, 10);
        shoppingCart.addItem(macBook, 1);

        ICampaign campaign1 = new RateCampaign(technologyCategory, 20.0, 3);
        ICampaign campaign2 = new RateCampaign(fruitCategory, 50.0, 5);
        ICampaign campaign3 = new AmountCampaign(fruitCategory, 5.0, 5);

        shoppingCart.applyDiscounts(campaign1, campaign2, campaign3);

        ICoupon coupon1 = new RateCoupon(1_000, 10);
        ICoupon coupon2 = new AmountCoupon(100_000, 1_000);

        shoppingCart.applyCoupon(coupon1);
        shoppingCart.applyCoupon(coupon2);

        shoppingCart.print();

    }
}

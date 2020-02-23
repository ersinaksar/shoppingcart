package cart;

import delivery.DeliveryCostCalculator;
import discount.campaign.AmountCampaign;
import discount.campaign.ICampaign;
import discount.campaign.RateCampaign;
import discount.coupon.AmountCoupon;
import discount.coupon.ICoupon;
import discount.coupon.RateCoupon;
import exception.InvalidPriceException;
import exception.InvalidQuantityException;
import exception.NullDataException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ShoppingCart implements IShoppingCart {

    private static final String PRODUCT_IS_NULL = "Product is NULL!";
    private static final String CATEGORY_IS_NULL = "Category is NULL!";
    private static final String PRODUCT_TITLE_IS_NULL = "Product title is NULL!";
    private static final String PRODUCT_PRICE_IS_INVALID = "Product price is invalid!";
    private static final String QUANTITY_IS_LESS_THAN_ONE = "Quantity of product is less than 1!";
    private static final String CAMPAIGNS_IS_NULL = "There is a no campaigns!";

    /**
     * Map<Product, Integer> shopping cart items
     * Product -> added product to cart
     * Integer -> quantity of this product
     */
    private Map<Product, Integer> shoppingCartItems = new HashMap<>();

    private double totalAmountAfterDiscounts;
    private double campaignDiscount;
    private double couponDiscount;

    public Map<Product, Integer> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(Map<Product, Integer> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public double getTotalAmountAfterDiscounts() {
        return totalAmountAfterDiscounts;
    }

    public void setTotalAmountAfterDiscounts(double totalAmountAfterDiscounts) {
        this.totalAmountAfterDiscounts = totalAmountAfterDiscounts;
    }

    public double getCampaignDiscount() {
        return campaignDiscount;
    }

    public double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCampaignDiscount(double campaignDiscount) {
        this.campaignDiscount = campaignDiscount;
    }


    /**
     * use to get delivery cost for this cart
     *
     * @return double delivery cost
     */
    @Override
    public double getDeliveryCost() {
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        if (shoppingCartItems.isEmpty()) return 0;
        return deliveryCostCalculator.calculateFor(this);
    }

    /**
     * use to add item to cart
     *
     * @param product  added product
     * @param quantity amount of the product
     */
    @Override
    public void addItem(Product product, int quantity) {

        //Check null product
        Optional.ofNullable(product).orElseThrow(() -> new NullDataException(PRODUCT_IS_NULL));

        //Check null category
        Optional.ofNullable(product.getCategory()).orElseThrow(() -> new NullDataException(CATEGORY_IS_NULL));

        //Check null product title
        Optional.ofNullable(product.getTitle()).orElseThrow(() -> new NullDataException(PRODUCT_TITLE_IS_NULL));

        //Check product price
        if (product.getPrice() <= 0) throw new InvalidPriceException(PRODUCT_PRICE_IS_INVALID);

        //Check quantity of product
        if (quantity < 1) throw new InvalidQuantityException(QUANTITY_IS_LESS_THAN_ONE);

        //Add product or update quantity of product
        if (!shoppingCartItems.containsKey(product)) shoppingCartItems.put(product, quantity);
        else shoppingCartItems.replace(product, shoppingCartItems.get(product) + quantity);

        totalAmountAfterDiscounts = getTotalPrice();
    }

    /**
     * use to apply campaign
     *
     * @param campaigns a lot of campaigns but applied one of them
     */
    @Override
    public void applyDiscounts(ICampaign... campaigns) {
        Optional.ofNullable(campaigns).orElseThrow(() -> new NullDataException(CAMPAIGNS_IS_NULL));
        totalAmountAfterDiscounts = getTotalPrice() - getMaximumAmountOfDiscount(campaigns);
    }

    private double getMaximumAmountOfDiscount(ICampaign... campaigns) {
        List<ICampaign> applicableCampaigns = getApplicableCampaigns(campaigns);
        double newDiscount = 0;

        for (ICampaign campaign : applicableCampaigns) {
            if (campaign instanceof AmountCampaign) {
                newDiscount = campaign.getDiscount(getPriceOfCategory(((AmountCampaign) campaign).getCategory().getTitle()));
            } else if (campaign instanceof RateCampaign) {
                newDiscount = campaign.getDiscount(getPriceOfCategory(((RateCampaign) campaign).getCategory().getTitle()));
            }
            if (campaignDiscount < newDiscount) {
                campaignDiscount = newDiscount;
            }
        }

        return campaignDiscount;
    }

    private double getPriceOfCategory(String categoryTitle) {
        return shoppingCartItems.keySet()
                .stream()
                .filter(product -> product.getCategory().getTitleAndParentsTitles().contains(categoryTitle))
                .mapToDouble(product -> product.getPrice() * shoppingCartItems.get(product))
                .sum();
    }

    private List<ICampaign> getApplicableCampaigns(ICampaign... campaigns) {
        Map<String, Long> categoriesAndProductCount = getCategoriesAndProductCount();
        Long quantityOfProduct;
        List<ICampaign> applicableCampaigns = new ArrayList<>();

        Optional.ofNullable(categoriesAndProductCount)
                .orElseThrow(NullDataException::new);

        for (ICampaign campaign : campaigns) {
            if (campaign instanceof AmountCampaign) {
                quantityOfProduct = categoriesAndProductCount.get(((AmountCampaign) campaign).getCategory().getTitle());
                if (quantityOfProduct != null && campaign.isApplicable(quantityOfProduct, ((AmountCampaign) campaign).getMinQuantityOfProduct()))
                    applicableCampaigns.add(campaign);
            } else if (campaign instanceof RateCampaign) {
                quantityOfProduct = categoriesAndProductCount.get(((RateCampaign) campaign).getCategory().getTitle());
                if (quantityOfProduct != null && campaign.isApplicable(quantityOfProduct, ((RateCampaign) campaign).getMinQuantityOfProduct()))
                    applicableCampaigns.add(campaign);
            }
        }
        return applicableCampaigns;
    }

    private Map<String, Long> getCategoriesAndProductCount() {

        List<String> categoryTitles = new ArrayList<>();

        for (Product product : shoppingCartItems.keySet()) {
            IntStream.range(0, shoppingCartItems.get(product))
                    .forEach(i -> categoryTitles.addAll(product.getCategory().getTitleAndParentsTitles()));
        }

        return categoryTitles
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private double getTotalPrice() {

        return shoppingCartItems.keySet()
                .stream()
                .mapToDouble(product -> product.getPrice() * shoppingCartItems.get(product))
                .sum();
    }

    public int getTotalQuantityOfProduct() {
        return this
                .getShoppingCartItems()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * use to apply coupon
     *
     * @param coupon planning to apply coupon
     */
    @Override
    public void applyCoupon(ICoupon coupon) {

        if ((coupon instanceof AmountCoupon
                && coupon.isApplicable(((AmountCoupon) coupon).getMinPurchaseAmount(), totalAmountAfterDiscounts)) ||
                (coupon instanceof RateCoupon
                        && coupon.isApplicable(((RateCoupon) coupon).getMinPurchaseAmount(), totalAmountAfterDiscounts))
        ) {
            couponDiscount += coupon.getDiscount(totalAmountAfterDiscounts);
            totalAmountAfterDiscounts -= coupon.getDiscount(totalAmountAfterDiscounts);
        }
    }

    private Map<Category, Map<Product, Long>> getCategoryBasedProduct() {

        Map<Category, Map<Product, Long>> categoryBasedProduct = new HashMap<>();

        for (Product product : shoppingCartItems.keySet()) {
            if (categoryBasedProduct.get(product.getCategory()) != null)
                categoryBasedProduct.get(product.getCategory()).put(product, Long.valueOf(shoppingCartItems.get(product)));
            else categoryBasedProduct.put(product.getCategory(), new HashMap<Product, Long>() {{
                put(product, Long.valueOf(shoppingCartItems.get(product)));
            }});
        }

        return categoryBasedProduct;
    }

    /**
     * use to print cart
     */
    @Override
    public void print() {
        Map<Category, Map<Product, Long>> categoryBasedProduct = getCategoryBasedProduct();
        categoryBasedProduct.forEach((key, value) -> {
            System.out.printf("-> Category Name: %s \n\n", key.getTitle());
            value.forEach((product, quantity) -> {
                System.out.printf("\t -> Product Name: %s \n", product.getTitle());
                System.out.printf("\t -> Quantity: %s \n", quantity);
                System.out.printf("\t -> Unit Price: %s \n\n", product.getPrice());
            });
        });
        System.out.printf("----> Total Price: %s \n", getTotalPrice());
        System.out.printf("----> Total Discount: %s \n", (getCampaignDiscount() + getCouponDiscount()));
        System.out.printf("----> Total Amount: %s \n", getTotalAmountAfterDiscounts());
        System.out.printf("----> Delivery Amount: %s \n", getDeliveryCost());
    }

}

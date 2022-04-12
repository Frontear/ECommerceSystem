/**
 * This class represents an item stored within a customers cart found in the
 * ECommerceSystem. Cart items are associated with a specific product. They
 * contain a reference to the product, as well as any options associated with
 * it.
 *
 * @author Ali Rizvi (501039655)
 * @see ECommerceSystem
 * @see Customer
 * @see Product
 */
public class CartItem {
    private final Product product;
    private final String options;

    /**
     * Constructs a new cart item with the given product and options.
     *
     * @param product The product associated with the cart item.
     * @param options The options associated with the cart item.
     */
    public CartItem(Product product, String options) {
        this.product = product;
        this.options = options;
    }

    /**
     * Getter for the product associated with the cart item.
     *
     * @return The product associated with the cart item.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Getter for the options associated with the cart item.
     *
     * @return The options associated with the cart item.
     */
    public String getOptions() {
        return options;
    }
}

public class CartItem {
    private final Product product;
    private final String options;

    public CartItem(Product product, String options) {
        this.product = product;
        this.options = options;
    }

    public Product getProduct() {
        return product;
    }

    public String getOptions() {
        return options;
    }
}

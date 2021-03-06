import java.util.Map;

/**
 * This class extends the Product class to create a Shoe product that can be
 * ordered via our ECommerceSystem. Shoes have stock options with sizes from 6
 * to 10, and colors between black and brown.
 *
 * Shoes can be ordered by customers. Each order can have variable options by
 * combining the sizes 6 to 10 and colors black and brown. The system will then
 * create a new shoe order for that customer, and manage it until the order is
 * either shipped or cancelled.
 *
 * @author Ali Rizvi (501039655)
 * @see ECSEcommerceSystem
 * @see Product
 * @see Customer
 * @see ProductOrder
 */
public class Shoes extends Product {
    // Key represents the colors, value represents the stock of that color, at an
    // integer array of 5 elements, storing data for sizes 6 to 10. To access the
    // array, 0th index should be considered stock for size 6, 1st index for size 7,
    // and so on until size 10.
    private final Map<String, int[]> stock;

    /**
     * Constructs a new shoe with the given name, id, price, and stock counts.
     *
     * @param name  The name of the product.
     * @param id    The unique identifier of the product, generated by the
     *              {@link ECommerceSystem}.
     * @param price The price of the product.
     * @param stock The stock counts for each size and color.
     */
    public Shoes(String name, String id, double price, Map<String, int[]> stock) {
        super(name, id, price, 0, Category.CLOTHING);

        this.stock = stock;
    }

    /**
     * Overrides {@link Product#validOptions(String)}. This implementation only
     * accepts a combination of sizes 6 to 10 and colors black and brown. Size must
     * be declared first, followed by a space to separate the size and color, and
     * then finally the color. It is case-insensitive. Internally, it uses
     * {@link String#equalsIgnoreCase(String)}.
     *
     * @param productOptions The product options to validate.
     * @return True if the product options are valid for this product.
     */
    @Override
    public boolean validOptions(String productOptions) {
        if (super.validOptions(productOptions)) {
            return false;
        }

        String[] split = productOptions.split(" ");
        if (split.length != 2) {
            return false;
        }

        String size = split[0];
        String color = split[1];

        return (size.equals("6") || size.equals("7") || size.equals("8") || size.equals("9") || size.equals("10"))
                && (color.equalsIgnoreCase("Black") || color.equalsIgnoreCase("Brown"));
    }

    /**
     * Overrides {@link Product#hasStock(String)}. This implementation checks if
     * there is stock available for the specified product options. It only accepts a
     * combination of sizes 6 to 10 and colors black and brown. Size must be
     * declared first, followed by a space to separate the size and color, and then
     * finally the color. It is case-insensitive.
     *
     * @param productOptions The product options to check stock for.
     * @return True if there is stock available for the product options specified.
     */
    @Override
    public boolean hasStock(String productOptions) {
        String[] split = productOptions.split(" ");

        String size = split[0];
        String color = split[1].toLowerCase();

        return stock.get(color)[getIndex(size)] > 0;
    }

    /**
     * Overrides {@link Product#reduceStock(String)}. This implementation reduces
     * the stock for the specified product options. It only accepts a combination of
     * sizes 6 to 10 and colors black and brown. Size must be declared first,
     * followed by a space to separate the size and color, and then finally the
     * color. It is case-insensitive.
     *
     * @param productOptions The product options to reduce stock for.
     */
    @Override
    public void reduceStock(String productOptions) {
        String[] split = productOptions.split(" ");

        String size = split[0];
        String color = split[1].toLowerCase();

        stock.get(color)[getIndex(size)]--;
    }

    /**
     * Overrides {@link Product#returnStock(String)}. This implementation increases
     * the stock for the specified product options. It only accepts a combination of
     * sizes 6 to 10 and colors black and brown. Size must be declared first,
     * followed by a space to separate the size and color, and then finally the
     * color. It is case-insensitive.
     *
     * @param productOptions The product options to increase stock for.
     */
    @Override
    public void returnStock(String productOptions) {
        String[] split = productOptions.split(" ");

        String size = split[0];
        String color = split[1].toLowerCase();

        stock.get(color)[getIndex(size)]++;
    }

    private int getIndex(String size) {
        return Integer.parseInt(size) - 6; // size 6: 0 index, size 7: 1 index etc.
    }
}

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a cart for a specific customer that contains items
 * found in the ECommerceSystem. Each cart holds multiple CartItems that are
 * associated with a specific product.
 *
 * @author Ali Rizvi (501039655)
 * @see ECommerceSystem
 * @see Customer
 * @see CartItem
 * @see Product
 */
public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    /**
     * Getter for the list of items in the cart.
     *
     * @return The list of items in the cart.
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Remove a specified item from the cart. If the item is not in the cart, this
     * method will throw an exception.
     *
     * @param productId The id of the product to remove.
     *
     * @throws Product.NotFoundException If the product is not in the cart.
     */
    public void removeItem(String productId) {
        Iterator<CartItem> it = items.iterator();

        while (it.hasNext()) {
            CartItem item = it.next();

            if (item.getProduct().getId().equals(productId)) {
                it.remove();
                return;
            }
        }

        throw new Product.NotFoundException(productId);
    }
}
